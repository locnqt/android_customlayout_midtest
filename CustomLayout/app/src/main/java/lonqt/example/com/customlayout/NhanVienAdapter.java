package lonqt.example.com.customlayout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by locnq on 4/18/2018.
 */

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private MainActivity context;
    private int resource;
    private ArrayList<NhanVien> objects;

    public NhanVienAdapter(@NonNull MainActivity context, int resource, @NonNull ArrayList<NhanVien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        ImageView imgNV = (ImageView) convertView.findViewById(R.id.imgNV);
        ImageView imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
        ImageView imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
        TextView tvTenNV = (TextView) convertView.findViewById(R.id.tvTenNV);
        TextView tvMaNV = (TextView) convertView.findViewById(R.id.tvMaNV);
//        CheckBox chbXoa = (CheckBox) convertView.findViewById(R.id.chbXoa);

        final NhanVien nhanVien = objects.get(position);

        if(nhanVien.isGioiTinh()){
            imgNV.setImageResource(R.drawable.male);
        }
        else {
            imgNV.setImageResource(R.drawable.female);
        }
        tvTenNV.setText("Tên: "+nhanVien.getHotenNV());
        tvMaNV.setText("Mã: "+nhanVien.getMaNV());

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("id nhan vien", "Get " + nhanVien.getId());
                context.dialogUpdate(nhanVien.getHotenNV(), nhanVien.getMaNV(), nhanVien.isGioiTinh(), nhanVien.getId());
            }
        });
//        chbXoa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    nhanVien.setChoosen(true);
//                }
//            }
//        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogDelete(nhanVien.getHotenNV(), nhanVien.getId());
            }
        });
        return convertView;
    }

    @Nullable
    @Override
    public NhanVien getItem(int position) {
        return this.objects.get(position);
    }
}
