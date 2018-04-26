package lonqt.example.com.customlayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvDanhSach;
    ImageView imgThem;
//    ImageView imgXoa;

    ArrayList<NhanVien> arrNhanvien;
    NhanVienAdapter adapterItemListView;

    DatabaseReference database;
    private long id;
    private long id2=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        database = FirebaseDatabase.getInstance().getReference();

        arrNhanvien = new ArrayList<>();

        adapterItemListView = new NhanVienAdapter(MainActivity.this, R.layout.layout_dong_nhanvien, arrNhanvien);
        lvDanhSach.setAdapter(adapterItemListView);

        getDataNhanvien(adapterItemListView);

        imgThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd();
            }
        });
//        imgXoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                deleteNhanVien(adapterItemListView);
//            }
//        });
    }

    private void getDataNhanvien(final NhanVienAdapter adapter) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id = dataSnapshot.getChildrenCount()-1;
                adapter.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    NhanVien nhanVien = ds.getValue(NhanVien.class);

                    Log.d("Get Data From Firebase", "Get " + nhanVien);
                    adapter.add(nhanVien);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        lvDanhSach = (ListView) findViewById(R.id.lvDanhsach);
        imgThem = (ImageView) findViewById(R.id.imgThem);
//        imgXoa = (ImageView) findViewById(R.id.imgXoa);
    }

    //
    private void dialogAdd() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_nhanvien);

        final EditText edtTennv = (EditText) dialog.findViewById(R.id.edtTennv);
        final EditText edtManv = (EditText) dialog.findViewById(R.id.edtManv);
        final RadioGroup rdg = (RadioGroup) dialog.findViewById(R.id.rdg);
        final RadioButton rdNam = (RadioButton) dialog.findViewById(R.id.rdNam);
        final RadioButton rdNu = (RadioButton) dialog.findViewById(R.id.rdNu);
        Button btadd = (Button) dialog.findViewById(R.id.btadd);
        Button bthuy = (Button) dialog.findViewById(R.id.btback);

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addTennv = edtTennv.getText().toString();
                String addManv = edtManv.getText().toString();
                boolean addGioiinh = true;
                if (addTennv.equals("") || addManv.equals("")) {
                    Toast.makeText(MainActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (rdNam.isChecked()) {
                        addGioiinh = true;
                    } else {
                        addGioiinh = false;
                    }
                    id2++;
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setHotenNV(addTennv);
                    nhanVien.setMaNV(addManv);
                    nhanVien.setGioiTinh(addGioiinh);
                    nhanVien.setId(id2);
                    database.child(String.valueOf(id2)).setValue(nhanVien);
                    Toast.makeText(MainActivity.this, "Đã thêm" + addTennv + "Có mã " + addManv + " vao list.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void dialogUpdate(final String tenNV, final String maNV, final boolean gioiTinh, final long idnv) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_nhanvien);

        final EditText edtEditTenNV = (EditText) dialog.findViewById(R.id.edtEditTenNV);
        final EditText edtEditMaNV = (EditText) dialog.findViewById(R.id.edtEditMaNV);
        final RadioGroup rdg = (RadioGroup) dialog.findViewById(R.id.rdgEdit);
        final RadioButton rdNam = (RadioButton) dialog.findViewById(R.id.rdEditNam);
        final RadioButton rdNu = (RadioButton) dialog.findViewById(R.id.rdEditNu);
        Button btedit = (Button) dialog.findViewById(R.id.btedit);
        Button bthuy = (Button) dialog.findViewById(R.id.btedback);

        edtEditTenNV.setText(tenNV);
        edtEditMaNV.setText(maNV);
        if (gioiTinh) {
            rdg.check(R.id.rdEditNam);
        } else {
            rdg.check(R.id.rdEditNu);
        }
        btedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEditTenNV.getText().toString().trim().equals("") || edtEditMaNV.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setHotenNV(edtEditTenNV.getText().toString().trim());
                    nhanVien.setMaNV(edtEditMaNV.getText().toString().trim());
                    if (rdNam.isChecked()) {
                        nhanVien.setGioiTinh(true);
                    } else {
                        nhanVien.setGioiTinh(false);
                    }
                    nhanVien.setId(idnv);
                    database.child(String.valueOf(idnv)).setValue(nhanVien);
                    Toast.makeText(MainActivity.this, "Đã cập nhật" + tenNV + " - " + maNV + " - " + nhanVien.isGioiTinh() + " vao list.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void dialogDelete(final String ten, final long idnv){
        final android.app.AlertDialog.Builder deletedialog = new android.app.AlertDialog.Builder(this);
        deletedialog.setTitle("Bán có muốn xóa Nhân viên "+ ten+" ko?");
        deletedialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        deletedialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.child(String.valueOf(idnv)).removeValue();
                Toast.makeText(MainActivity.this, "Đã xóa nhân viên"+ten+" vao list.", Toast.LENGTH_SHORT).show();
            }

        });
        deletedialog.show();
    }
}
