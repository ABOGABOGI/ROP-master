package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Winardi on 10/23/2017.
 */

public class BusinessResponse {
    private int id;
    @SerializedName("jenis_usaha")
    private String jenisUsaha;
    private String ket;
    private int stat;

    public BusinessResponse(String jenisUsaha, String ket) {
        this.jenisUsaha = jenisUsaha;
        this.ket = ket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJenisUsaha() {
        return jenisUsaha;
    }

    public void setJenisUsaha(String jenisUsaha) {
        this.jenisUsaha = jenisUsaha;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }
}
