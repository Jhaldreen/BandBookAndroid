package com.antonio.bandbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.zabk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText correo,pass,nombre,telefono,localidad,comunidad,num;
    Button registrase;

    FirebaseAuth firebaseAuth;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        correo = findViewById(R.id.editEmailReg);
        pass = findViewById(R.id.passReg);
        nombre = findViewById(R.id.nombreReg);
        telefono = findViewById(R.id.editTextPhone);
        localidad = findViewById(R.id.localidadReg);
        comunidad = findViewById(R.id.provinvinciaReg);
        num = findViewById(R.id.numeroReg);
        //IMportante crear la instancia de FirebaseAuth
        firebaseAuth =FirebaseAuth.getInstance();

        registrase = findViewById(R.id.RegButton);

        registrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = correo.getText().toString();
                String password = pass.getText().toString();

                //validamos
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    correo.setError("Email no válido");
                    correo.setFocusable(true);

                }else if (pass.length()<6){
                    pass.setFocusable(true);
                }else{
                    registrarUsuario(email,password);
                }


            }
        });
    }

    /*METODO PARA REGISTRAR UN USUARIO BANDA*/
    private void registrarUsuario(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                /* Si el registro es correcto*/

                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    //Datos que se registran
                    //han de ser diferentes al edittext
                    assert user !=null;// afirmamos que el usuario no es nulo
                    String uid = user.getUid();//para obetner el ID
                    String email = correo.getText().toString();
                    String password = pass.getText().toString();
                    String nom = nombre.getText().toString();
                    String phone = telefono.getText().toString();
                    String loca = localidad.getText().toString();
                    String com = comunidad.getText().toString();
                    String numeroBan = num.getText().toString();

                    //Se crea un hamap para mandar los datos a firebase
                    HashMap<Object,String> DatosUsuario = new HashMap<>();

                    DatosUsuario.put("uid",uid);
                    DatosUsuario.put("email", email);
                    DatosUsuario.put("pass",password);
                    DatosUsuario.put("nombreBanda",nom);
                    DatosUsuario.put("phone",phone);
                    DatosUsuario.put("localidad",loca);
                    DatosUsuario.put("provincia",com);
                    DatosUsuario.put("numBand",numeroBan);

                    DatosUsuario.put("imagen","");//demomento vacio

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    //Se crea la base de datos no relacional con un nombre
                    DatabaseReference reference = database.getReference("Usuarios_Banda");
                    Toast.makeText(Register.this, "Registro completado", Toast.LENGTH_SHORT).show();
                    //UNA VEZ REGISTRADO VAMOS A LA PANTALLA DE PERFIL
                    startActivity(new Intent(Register.this,ProfileBanda.class));
                }else{
                    Toast.makeText(Register.this, "UPS Algo salió mal", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }
    }
