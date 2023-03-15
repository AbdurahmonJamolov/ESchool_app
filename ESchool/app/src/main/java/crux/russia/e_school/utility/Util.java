package crux.russia.e_school.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import crux.russia.e_school.interfaces.LoadData;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.User;
import crux.russia.e_school.model.UserAchievements;

public class Util {

    public static int getItemIndexFromListOfClassRoom(String val, List<ClassRoom> list) {
        int index = 0;
        for (ClassRoom item : list) {
            if (item.getClassName().equals(val)) {
                return index;
            } else {
                index++;
            }
        }
        return 0;
    }

    public static int getItemIndexFromListOfMaterials(String val, List<Materials> list) {
        int index = 0;
        for (Materials item : list) {
            if (item.getMaterialName().equals(val)) {
                return index;
            } else {
                index++;
            }
        }
        return 0;
    }

    public static boolean allEditTextIsFill(TextInputEditText[] objects) {
        boolean fieldFill = true;
        for (TextInputEditText e : objects) {
            if (e.getText().toString().isEmpty()) {
                e.setError(e.getHint() + " is Mandatory ");
                fieldFill = false;
            }
        }
        return fieldFill;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static void addOrUpdateObject(Object object, String docId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(object.getClass().getSimpleName()).document(docId).set(object);
    }

    public static void deleteObject(Object object, String docId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(object.getClass().getSimpleName()).document(docId).delete();
    }


    public static void loadAllClassRoom(final LoadData interfaceOb) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(ClassRoom.class.getSimpleName()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<ClassRoom> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                ClassRoom object = new ClassRoom();
                                object.setDocId(documentSnapshot.getId());
                                object.setClassName(documentSnapshot.getString("className"));
                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllClassRoom(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }

    public static void loadAllMaterials(final LoadData interfaceOb) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Materials.class.getSimpleName()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Materials> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Materials object = new Materials();
                                object.setDocId(documentSnapshot.getId());
                                object.setClassDocId(documentSnapshot.getString("classDocId"));
                                object.setClassName(documentSnapshot.getString("className"));
                                object.setMaterialName(documentSnapshot.getString("materialName"));

                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllMaterials(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }

    public static void loadAllMaterialsByClassRoom(final LoadData interfaceOb, String classDocId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Materials.class.getSimpleName()).whereEqualTo("classDocId", classDocId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Materials> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Materials object = new Materials();
                                object.setDocId(documentSnapshot.getId());
                                object.setClassDocId(documentSnapshot.getString("classDocId"));
                                object.setClassName(documentSnapshot.getString("className"));
                                object.setMaterialName(documentSnapshot.getString("materialName"));

                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllMaterials(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }

    public static void loadAllMaterialsContantByMaterials(final LoadData interfaceOb, String materialsDocId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(MaterialsContant.class.getSimpleName()).whereEqualTo("materialDocId", materialsDocId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<MaterialsContant> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                MaterialsContant object = new MaterialsContant();
                                object.setDocId(documentSnapshot.getId());
                                object.setMaterialName(documentSnapshot.getString("materialName"));
                                object.setMaterialDocId(documentSnapshot.getString("materialDocId"));
                                object.setTitle(documentSnapshot.getString("title"));
                                object.setBody(documentSnapshot.getString("body"));
                                object.setLink(documentSnapshot.getString("link"));

                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllMaterialsContant(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }

    public static void loadAllMaterialsContent(final LoadData interfaceOb) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(MaterialsContant.class.getSimpleName()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<MaterialsContant> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                MaterialsContant object = new MaterialsContant();
                                object.setDocId(documentSnapshot.getId());
                                object.setMaterialName(documentSnapshot.getString("materialName"));
                                object.setMaterialDocId(documentSnapshot.getString("materialDocId"));
                                object.setTitle(documentSnapshot.getString("title"));
                                object.setBody(documentSnapshot.getString("body"));
                                object.setLink(documentSnapshot.getString("link"));

                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllMaterialsContant(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }

    public static void loadAllExamByMaterials(final LoadData interfaceOb, String materialsDocId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Exam.class.getSimpleName()).whereEqualTo("materialDocId", materialsDocId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Exam> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Exam object = new Exam();
                                object.setDocId(documentSnapshot.getId());
                                object.setMaterialsId(documentSnapshot.getString("materialsId"));
                                object.setMaterialsName(documentSnapshot.getString("materialsName("));
                                object.setExamTitl(documentSnapshot.getString("examTitl"));
                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllExam(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }

    public static void loadAllExam(final LoadData interfaceOb) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Exam.class.getSimpleName()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Exam> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Exam object = new Exam();
                                object.setDocId(documentSnapshot.getId());
                                object.setMaterialsId(documentSnapshot.getString("materialsId"));
                                object.setMaterialsName(documentSnapshot.getString("materialsName"));
                                object.setExamTitl(documentSnapshot.getString("examTitl"));
                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllExam(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }

    public static void loadAllQuestionByExam(final LoadData interfaceOb, String examDocId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Question.class.getSimpleName()).whereEqualTo("examDocId", examDocId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Question> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Question object = new Question();
                                object.setDocId(documentSnapshot.getId());
                                object.setExamDocId(documentSnapshot.getString("examDocId"));
                                object.setExamTitle(documentSnapshot.getString("examTitle"));
                                object.setQuestionBody(documentSnapshot.getString("questionBody"));
                                object.setCorrectAnswer(documentSnapshot.getBoolean("correctAnswer"));

                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllQuestion(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }

    public static void loadAllAchievementsByUser(final LoadData interfaceOb, String userDocId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(UserAchievements.class.getSimpleName()).whereEqualTo("userDocId", userDocId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<UserAchievements> list = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                UserAchievements object = new UserAchievements();
                                object.setDocId(documentSnapshot.getId());
                                object.setUserDocId(documentSnapshot.getString("userDocId"));
                                object.setExamTitle(documentSnapshot.getString("examTitle"));
                                object.setMark(documentSnapshot.getString("mark"));
                                object.setDate(documentSnapshot.getString("date"));

                                list.add(object);
                                Log.d("Tracking", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                            interfaceOb.loadAllAchievements(list);
                        } else {
                            Log.d("Test", "Error getting documents: ");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Load Items", "Error getting documents: ", e);
                    }
                });
    }
}
