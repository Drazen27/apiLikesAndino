package com.apilikes.ApiRestLikes.repository;

import com.apilikes.ApiRestLikes.Entities.FirebaseDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
// import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.ByteArrayInputStream;
// import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;



@Repository
public class FirestoreRepository {
    private  String CollectionName;
    private  Firestore firestoreDb;

   public FirestoreRepository(String CollectionName) throws IOException {
        // Cargar variables de entorno desde el archivo .env
        Dotenv dotenv = Dotenv.configure().load();

        // Obtener las variables de entorno necesarias
        String projectId = dotenv.get("PROJECT_ID");
        String privateKeyId = dotenv.get("PRIVATE_KEY_ID");
        String privateKey = dotenv.get("PRIVATE_KEY").replace("\\n", "\n");
        String clientEmail = dotenv.get("CLIENT_EMAIL");
        String clientId = dotenv.get("CLIENT_ID");

        // Crear credenciales personalizadas
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "service_account");
        credentials.put("project_id", projectId);
        credentials.put("private_key_id", privateKeyId);
        credentials.put("private_key", privateKey);
        credentials.put("client_email", clientEmail);
        credentials.put("client_id", clientId);
        credentials.put("auth_uri", dotenv.get("AUTH_URI"));
        credentials.put("token_uri", dotenv.get("TOKEN_URI"));
        credentials.put("auth_provider_x509_cert_url", dotenv.get("AUTH_PROVIDER_X509_CERT_URL"));
        credentials.put("client_x509_cert_url", dotenv.get("CLIENT_X509_CERT_URL"));

        // Convertir el mapa de credenciales a un InputStream
        String credentialsJson = new ObjectMapper().writeValueAsString(credentials);
        ByteArrayInputStream credentialsStream = new ByteArrayInputStream(credentialsJson.getBytes());

        // Inicializar FirebaseApp solo si no se ha inicializado
        if (FirebaseApp.getApps().isEmpty()) {
            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                .setProjectId(projectId)
                .build();
            FirebaseApp.initializeApp(options);
        }

        this.firestoreDb = FirestoreClient.getFirestore();
        this.CollectionName = CollectionName;
    }
    
    public FirestoreRepository() {
        
    }

    public <T extends FirebaseDocument> T getById(Class<T> clazz,String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestoreDb.collection(CollectionName).document(id);
        DocumentSnapshot snapshot = docRef.get().get();
        if (snapshot.exists()) {
            T record = snapshot.toObject(clazz);
            if (record != null) {
                record.setId(snapshot.getId());
            }
            return record;
        }
        return null;
    }

    public <T extends FirebaseDocument> List<T> getAll(Class<T> clazz) throws ExecutionException, InterruptedException {
        CollectionReference collectionRef = firestoreDb.collection(CollectionName);
        ApiFuture<QuerySnapshot> querySnapshot = collectionRef.get();

        List<T> userList = new ArrayList<>();
        for (DocumentSnapshot snapshot : querySnapshot.get().getDocuments()) {
            if (snapshot.exists()) {
                T record = snapshot.toObject(clazz);
                if (record != null) {
                    record.setId(snapshot.getId());
                    userList.add(record);
                }
            }
        }
        return userList.isEmpty() ? null : userList;
    }

    public <T extends FirebaseDocument> T add(T model) throws ExecutionException, InterruptedException {
        try {
            CollectionReference collectionReference = firestoreDb.collection(CollectionName);
            ApiFuture<DocumentReference> newDocument = collectionReference.add(model);
            model.setId(newDocument.get().getId());
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends FirebaseDocument> T update(String id, T model) throws ExecutionException, InterruptedException {
        try {
            CollectionReference collectionReference = firestoreDb.collection(CollectionName);
            DocumentReference docRef = collectionReference.document(id);

            // Obtener todas las propiedades públicas del objeto
            Field[] fields = model.getClass().getDeclaredFields();
            List<String> fieldNames = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(model) != null && !field.getName().equals("id")) {
                    fieldNames.add(field.getName());
                }
            }

            docRef.set(model, SetOptions.mergeFields(fieldNames)).get();
            model.setId(id); 
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(String id) throws ExecutionException, InterruptedException {
        try {
            CollectionReference collectionReference = firestoreDb.collection(CollectionName);
            DocumentReference docRef = collectionReference.document(id);
            ApiFuture<WriteResult> writeResult = docRef.delete();
            writeResult.get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T extends FirebaseDocument> List<T> search(Class<T> clazz, T model) throws ExecutionException, InterruptedException, IllegalAccessException {
        try {
            CollectionReference collectionReference = firestoreDb.collection(CollectionName);
            Query query = collectionReference;
    
            // Obtener todas las propiedades públicas del objeto
            Field[] fields = model.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(model);
                if (value != null) {
                    query = query.whereEqualTo(field.getName(), value);
                }
            }
    
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<T> results = new ArrayList<>();
            for (DocumentSnapshot documentSnapshot : querySnapshot.get().getDocuments()) {
                T record = documentSnapshot.toObject(clazz);
                if (record != null) {
                    record.setId(documentSnapshot.getId());
                    results.add(record);
                }
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends FirebaseDocument> List<T> searchExact(Class<T> clazz, T model) throws ExecutionException, InterruptedException, IllegalAccessException {
        try {
            CollectionReference collectionReference = firestoreDb.collection(CollectionName);
            Query query = collectionReference;
    
            Field[] fields = model.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(model);
                if (value != null) {
                    query = query.whereEqualTo(field.getName(), value);
                }
            }
    
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<T> results = new ArrayList<>();
            for (DocumentSnapshot documentSnapshot : querySnapshot.get().getDocuments()) {
                T record = documentSnapshot.toObject(clazz);
                if (record != null) {
                    boolean allMatch = true;
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object fieldValue = field.get(model);
                        Object recordValue = field.get(record);
                        if (fieldValue != null && !fieldValue.equals(recordValue)) {
                            allMatch = false;
                            break;
                        }
                    }
                    if (allMatch) {
                        record.setId(documentSnapshot.getId());
                        results.add(record);
                    }
                }
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    // private <T> String getCollectionName(Class<T> clazz) {
    //     // Deberías definir una forma de obtener el nombre de la colección en base a la clase
    //     // Por ejemplo, usando una convención de nombres, anotaciones, o un mapa de clases a nombres de colección
    //     return clazz.getSimpleName().toLowerCase() + "s";
    // }
}
