package com.bayex.bayex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.bayex.bayex.Dialogs.BayFilesDialog;
import com.bayex.bayex.Utils.Conversion;
import com.bayex.bayex.Utils.Files;
import com.bayex.bayex.Utils.Permissions;

import java.io.File;
import java.util.ArrayList;

import static com.bayex.bayex.Dialogs.BayFilesDialog.baySelectedBase;

public class MainActivity extends AppCompatActivity {

    private Button bLoadBase;

    private Files files;
    private Permissions permissions;

    private ArrayList<File> bayFiles;

    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        bLoadBase = findViewById(R.id.bLoadBase);
        bLoadBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prepareApp()){
                    new BayFilesDialog(bayFiles);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Brak plików bazy wiedzy", Toast.LENGTH_SHORT).show();
                    bLoadBase.setEnabled(false);
                }
            }
        });

        files = new Files();
        permissions = new Permissions();

    }

    public Boolean prepareApp(){
        files.createFolder("");

        bayFiles = files.findBayFiles();
        if(bayFiles == null){
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Conversion.byteMoved = 0;
        baySelectedBase = null;
    }
}
