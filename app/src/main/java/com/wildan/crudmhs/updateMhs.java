package com.wildan.crudmhs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class updateMhs extends AppCompatActivity {

    EditText kdEdit,nmEdit,hgEdit;
    Button tblEdit,tblDelete,balik;
    private String kode,nama_barang,harga;
    public static TextView textView;
    private String URL_UPDATE = "http://192.168.43.68:8080/utsmobile2/updateMhs.php";
    private String URL_TAMPIL = "http://192.168.43.68:8080/utsmobile2/tampilMhs.php";
    private String URL_DELETE = "http://192.168.43.68:8080/utsmobile2/hapusMhs.php?kode=";
    private String TAG_KODE = "kode";
    private String TAG_NAMA = "nama";
    private String TAG_TGL = "tgl_lhr";
    private String TAG_JENKEL = "jenkel";
    private String TAG_AGAMA = "agama";
    private String TAG_GOLDARAH = "goldarah";
    private String TAG_STATUS = "status";
    private String TAG_KOTA = "kota";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mhs);
        Intent intent = getIntent();
        kode = intent.getStringExtra(TAG_KODE);
        kdEdit=(EditText)findViewById(R.id.xkodeEdit);
        nmEdit=(EditText)findViewById(R.id.xnamaEdit);
        hgEdit=(EditText)findViewById(R.id.xhargaEdit);
        balik=(Button)findViewById(R.id.tblBack);
        tblEdit=(Button)findViewById(R.id.tblupdate);
        tblDelete=(Button)findViewById(R.id.tbldelete);

        kdEdit.setText(kode);
        nmEdit.setText(nama_barang);
        hgEdit.setText(harga);
        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(update_Barang.this,MainActivity.class);
                startActivity(intent1);
            }
        });
        tblDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteBarang();
            }
        });
        tblEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_Barang();
                Intent intent1=new Intent(update_Barang.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void update_Barang() {
        final String kode = kdEdit.getText().toString();
        final String nama_barang = nmEdit.getText().toString();
        final String harga = hgEdit.getText().toString();
        class updateBarang extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(TAG_ID, kode);
                params.put(TAG_NAMA, nama_barang);
                params.put(TAG_HARGA, harga);
                RequestHandler rh = new RequestHandler();
                String hs = rh.sendPostRequest(URL_UPDATE_BRG, params);
                return hs;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
            }
        }
        updateBarang ub = new updateBarang();
        ub.execute();
    }

    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(update_Barang.this, "Update...", "Tunggu...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(URL_DELETE_BRG, kode);
                return s;
            }
        }
        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteBarang(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin Ingin Menghapus Barang ini ?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new Intent(update_Barang.this,MainActivity.class));
                    }
                });
        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
