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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class updateMhs extends AppCompatActivity {

    private EditText kdEdit,nmEdit,tglEdit,ktEdit;
    private Button tblEdit,tblDelete;
    private RadioGroup rgJenkel, rgGol, rgStatus;
    private RadioButton rbJenkel, rbGol, rbStatus, rbJenkelL, rbJenkelP, rbGolA, rbGolB, rbGolAB, rbGolO, rbStatusA, rbStatusTA;
    private String kode,nama,tanggal, kota, gol, jenkel, status, agm;
    private Spinner agama;
    private String URL_UPDATE = "http://192.168.43.68:8080/utsmobile2/updateMhs.php";
    private String URL_DELETE = "http://192.168.43.68:8080/utsmobile2/hapusMhs.php?kode=";
    private String TAG_KODE = "kode";
    private String TAG_NAMA = "nama";
    private String TAG_TGL = "tgl_lhr";
    private String TAG_JENKEL = "jenkel";
    private String TAG_AGAMA = "agama";
    private String TAG_GOLDARAH = "goldarah";
    private String TAG_STATUS = "status";
    private String TAG_KOTA = "kota";
    //private String[] listAgama = new String[].getString(R.array.agama);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mhs);

        getSupportActionBar().setTitle("Update Mahasiswa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        kode = intent.getStringExtra(TAG_KODE);
        nama = intent.getStringExtra(TAG_NAMA);
        tanggal = intent.getStringExtra(TAG_TGL);
        kota = intent.getStringExtra(TAG_KOTA);
        gol = intent.getStringExtra(TAG_GOLDARAH);
        jenkel = intent.getStringExtra(TAG_JENKEL);
        status = intent.getStringExtra(TAG_STATUS);
        agm = intent.getStringExtra(TAG_AGAMA);

        kdEdit = (EditText)findViewById(R.id.input_kode);
        nmEdit = (EditText)findViewById(R.id.input_nama);
        tglEdit = (EditText)findViewById(R.id.input_tanggal);
        ktEdit = (EditText)findViewById(R.id.input_kota);
        rgJenkel = (RadioGroup)findViewById(R.id.rgJenkel);
        rgGol = (RadioGroup)findViewById(R.id.rgGol);
        rgStatus = (RadioGroup)findViewById(R.id.rgStatus);
        tblEdit=(Button)findViewById(R.id.btn_update);
        tblDelete=(Button)findViewById(R.id.btn_delete);

        kdEdit.setText(kode);
        nmEdit.setText(nama);
        tglEdit.setText(tanggal);
        ktEdit.setText(kota);

        rbGolA = (RadioButton)findViewById(R.id.golA);
        rbGolB = (RadioButton)findViewById(R.id.golB);
        rbGolAB = (RadioButton)findViewById(R.id.golAB);
        rbGolO = (RadioButton)findViewById(R.id.golO);

        rbJenkelL = (RadioButton)findViewById(R.id.jenkelL);
        rbJenkelP = (RadioButton)findViewById(R.id.jenkelP);

        rbStatusA = (RadioButton)findViewById(R.id.statusA);
        rbStatusTA = (RadioButton)findViewById(R.id.statusTA);

        if (gol.equals("A")){
            rbGolA.setChecked(true);
        }else if (gol.equals("B")){
            rbGolB.setChecked(true);
        }else if (gol.equals("AB")){
            rbGolAB.setChecked(true);
        }else{
            rbGolO.setChecked(true);
        }

        if (jenkel.equals("Perempuan")){
            rbJenkelP.setChecked(true);
        }else {
            rbJenkelL.setChecked(true);
        }

        if (status.equals("Aktif")){
            rbStatusA.setChecked(true);
        }else {
            rbStatusTA.setChecked(true);
        }

        agama = (Spinner)findViewById(R.id.spinAgama);
        tblDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
        tblEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });
    }

    private void update_mhs() {
        final String kode = kdEdit.getText().toString();
        final String nama = nmEdit.getText().toString();
        final String tanggal = tglEdit.getText().toString();
        final String kota = ktEdit.getText().toString();

        String bjenkel, bgol, bstatus, agm;

        int selectedRgJenkel = rgJenkel.getCheckedRadioButtonId();
        rbJenkel = (RadioButton) findViewById(selectedRgJenkel);

        int selectedRgGol = rgGol.getCheckedRadioButtonId();
        rbGol = (RadioButton) findViewById(selectedRgGol);

        int selectedRgStatus = rgStatus.getCheckedRadioButtonId();
        rbStatus = (RadioButton) findViewById(selectedRgStatus);

        if (rbJenkel.getText().equals("Perempuan")){
            bjenkel = "0";
        }else {
            bjenkel = "1";
        }

        if (rbGol.getText().equals("A")){
            bgol = "1";
        }else if (rbGol.getText().equals("B")){
            bgol = "2";
        }else if (rbGol.getText().equals("AB")){
            bgol = "3";
        }else {
            bgol = "4";
        }

        if (rbStatus.getText().equals("Tidak Aktif")){
            bstatus = "0";
        }else {
            bstatus = "1";
        }

        if (agama.getSelectedItem().equals("Islam")){
            agm = "1";
        }else if (agama.getSelectedItem().equals("Kristen")){
            agm = "2";
        }else if (agama.getSelectedItem().equals("Katholik")){
            agm = "3";
        }else if (agama.getSelectedItem().equals("Hindu")){
            agm = "4";
        }else if (agama.getSelectedItem().equals("Budha")){
            agm = "5";
        }else {
            agm = "6";
        }

        final String jenkel = bjenkel;
        final String fAgm = agm;
        final String gol = bgol;
        final String status = bstatus;

        class update_Mhs extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(TAG_KODE, kode);
                params.put(TAG_NAMA, nama);
                params.put(TAG_TGL, tanggal);
                params.put(TAG_JENKEL, jenkel);
                params.put(TAG_AGAMA, fAgm);
                params.put(TAG_GOLDARAH, gol);
                params.put(TAG_STATUS, status);
                params.put(TAG_KOTA, kota);
                RequestHandler rh = new RequestHandler();
                String hs = rh.sendPostRequest(URL_UPDATE, params);
                return hs;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
            }
        }
        update_Mhs um = new update_Mhs();
        um.execute();
    }

    private void deleteMhs(){
        class DeleteMhs extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(updateMhs.this, "Update...", "Tunggu...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(URL_DELETE, kode);
                return s;
            }
        }
        DeleteMhs dm = new DeleteMhs();
        dm.execute();
    }

    private void confirmDelete(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin Ingin Menghapus Data ini ?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteMhs();
                        Toast.makeText(updateMhs.this, "Berhasil Dihapus !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(updateMhs.this,MainActivity.class));
                        finish();
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

    private void validasi(){
        String form_kode, form_nama, form_tgl, form_kota;
        form_kode = kdEdit.getText().toString();
        form_nama = nmEdit.getText().toString();
        form_tgl = tglEdit.getText().toString();
        form_kota = ktEdit.getText().toString();

        if (form_kode.isEmpty()){
            kdEdit.setError("Kode Belum Diisi");
            kdEdit.requestFocus();
        }else if (form_nama.isEmpty()){
            nmEdit.setError("Nama Belum Diisi");
            nmEdit.requestFocus();
        }else if (form_tgl.isEmpty()){
            tglEdit.setError("Tanggal Lahir Belum Diisi");
            tglEdit.requestFocus();
        }else if (form_kota.isEmpty()){
            ktEdit.setError("Kota Belum Diisi");
            ktEdit.requestFocus();
        }else if (rgJenkel.getCheckedRadioButtonId() == -1){
            Toast.makeText(updateMhs.this, "Pilih Jenis Kelamin !", Toast.LENGTH_SHORT).show();
        }else if (rgGol.getCheckedRadioButtonId() == -1){
            Toast.makeText(updateMhs.this, "Pilih Golongan Darah !", Toast.LENGTH_SHORT).show();
        }else if (rgStatus.getCheckedRadioButtonId() == -1){
            Toast.makeText(updateMhs.this, "Pilih Status Mahasiswa !", Toast.LENGTH_SHORT).show();
        }else {
            update_mhs();
            Toast.makeText(updateMhs.this, "Berhasil Ditambahkan !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(updateMhs.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

/**
 * Created by Wildan Rozaqi
 * A22.2018.02690
 */