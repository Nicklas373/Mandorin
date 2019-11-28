package id.hana.mandorin;

public class GetRiwayatPembayaranAdapter{

    public String id, nomor_kontrak, nama_pemesan, email, alamat, status, data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }

    public String getNomor_kontrak() { return nomor_kontrak; }

    public void setNomor_kontrak(String nomor_kontrak) {
        this.nomor_kontrak= nomor_kontrak;
    }

    public String getNama_pemesan() {
        return nama_pemesan;
    }

    public void setNama(String nama_pemesan) {
        this.nama_pemesan= nama_pemesan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat= alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status= status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data= data;
    }
}