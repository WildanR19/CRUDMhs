package com.wildan.crudmhs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import static java.lang.String.valueOf;

public class addMhs extends AppCompatActivity {

    private EditText teksKode, teksNama, tanggal, kota;
    private RadioGroup rgJenkel, rgGol, rgStatus;
    private RadioButton rbJenkel, rbGol, rbStatus;
    private Button tbSave;
    private Spinner agama;
    private String URL_ADD = "http://192.168.43.68:8080/utsmobile2/tambahMhs.php";
    private String KEY_KODE = "kode";
    private String KEY_NAMA = "nama";
    private String KEY_TGL = "tgl_lhr";
    private String KEY_JENKEL = "jenkel";
    private String KEY_AGAMA = "agama";
    private String KEY_GOLDARAH = "goldarah";
    private String KEY_STATUS = "status";
    private String KEY_KOTA = "kota";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mhs);

        getSupportActionBar().setTitle("Tambah Mahasiswa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agama = (Spinner) findViewById(R.id.spinAgama);
        teksKode = (EditText) findViewById(R.id.input_kode);
        teksNama = (EditText) findViewById(R.id.input_nama);
        tanggal = (EditText) findViewById(R.id.input_tanggal);
        kota = (EditText) findViewById(R.id.input_kota);
        tbSave = (Button) findViewById(R.id.btn_tambah);

        rgJenkel = (RadioGroup) findViewById(R.id.rgJenkel);
        rgGol = (RadioGroup) findViewById(R.id.rgGol);
        rgStatus = (RadioGroup) findViewById(R.id.rgStatus);

        tbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });
    }

    private void simpanData() {
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

        final String kode = teksKode.getText().toString();
        final String nama = teksNama.getText().toString();
        final String tgl = tanggal.getText().toString();
        final String jenkel = bjenkel;
        final String fAgm = agm;
        final String gol = bgol;
        final String status = bstatus;
        final String kt = kota.getText().toString();

        class SimpanData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KEY_KODE, kode);
                params.put(KEY_NAMA, nama);
                params.put(KEY_TGL, tgl);
                params.put(KEY_JENKEL, jenkel);
                params.put(KEY_AGAMA, fAgm);
                params.put(KEY_GOLDARAH, gol);
                params.put(KEY_STATUS, status);
                params.put(KEY_KOTA, kt);
                RequestHandler rh = new RequestHandler();
                String hs = rh.sendPostRequest(URL_ADD, params);
                return hs;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
            }
        }
        SimpanData sd = new SimpanData();
        sd.execute();
    }

    private void validasi(){
        String form_kode, form_nama, form_tgl, form_kota;
        form_kode = teksKode.getText().toString();
        form_nama = teksNama.getText().toString();
        form_tgl = tanggal.getText().toString();
        form_kota = kota.getText().toString();

        if (form_kode.isEmpty()){
            teksKode.setError("Kode Belum Diisi");
            teksKode.requestFocus();
        }else if (form_nama.isEmpty()){
            teksNama.setError("Nama Belum Diisi");
            teksNama.requestFocus();
        }else if (form_tgl.isEmpty()){
            tanggal.setError("Tanggal Lahir Belum Diisi");
            tanggal.requestFocus();
        }else if (form_kota.isEmpty()){
            kota.setError("Kota Belum Diisi");
            kota.requestFocus();
        }else {
            simpanData();
            Toast.makeText(addMhs.this, "Sukses Sitambhkan !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(addMhs.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
