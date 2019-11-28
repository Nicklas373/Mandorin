package id.hana.mandorin;

public class GetDataKomplainAdapter {

    public String id, nomor_kontrak, status, status_komplain, alamat, komplain, email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }

    public String getNomor_kontrak() {
        return nomor_kontrak;
    }

    public void setNomor_kontrak(String nomor_kontrak) {
        this.nomor_kontrak= nomor_kontrak;
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

    public void setStatus_komplain(String status_komplain) {
        this.status_komplain= status_komplain;
    }

    public String getStatus_komplain() {
        return status_komplain;
    }

    public void setStatus(String status) {
        this.status= status;
    }

    public String getKomplain() {
        return komplain;
    }

    public void setKomplain(String komplain) { this.komplain= komplain; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }
}
