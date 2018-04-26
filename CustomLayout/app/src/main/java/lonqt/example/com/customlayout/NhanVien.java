package lonqt.example.com.customlayout;

/**
 * Created by locnq on 4/18/2018.
 */

public class NhanVien {
    private String hotenNV;
    private String maNV;
    private boolean gioiTinh;
    private boolean choosen;
//    private boolean gioiTinh, chosen = false;
    private long id;

    public NhanVien() {
    }

    public String getHotenNV() {
        return hotenNV;
    }

    public void setHotenNV(String hotenNV) {
        this.hotenNV = hotenNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isChoosen() {
        return choosen;
    }

    public void setChoosen(boolean choosen) {
        this.choosen = choosen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
