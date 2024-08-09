# Java DB Submission Project (challenge javadb)

Membuat struktur database dan Aplikasi rekapitulasi menu otomatis dari Warung Makan Samudra (WMS)

## Fitur

- Insert Data Transaksi
- Update Data Transaksi
- Menampilkan Kode Cabang Warung
- Menampilkan Data Produk
- Menampilkan Data Transaksi
- Menampilkan Semua Data Transaksi

## Tools

- JDK (Java Development Kit) versi 21
- MySQL versi 9.0.0

## Struktur database

Desain database yang sudah dibuat sesuai dengan kaidah normalisasi adalah sebagai berikut

![WMS DB Project](https://github.com/user-attachments/assets/38321e93-3597-4159-bb87-819ab59c16bb)

Keterangan :
- Pada kolom `type` tabel `transactions` menggunakan enumerasi dengan nilai `EAT_IN`, `TAKE_AWAY`, dan `ONLINE`.

## Instalasi

1. Clone repository ini ke mesin lokal Anda:
    ```bash
    git clone https://git.enigmacamp.com/enigma-20/muhammad-rasyaddany-prasetyo/challenge-javadb.git
    ```

2. Navigasi ke direktori proyek

3. Pastikan Anda memiliki file `ConnectionUtil.java` yang berisi konfigurasi koneksi database Anda.

4. Buat database dan tabel yang diperlukan di MySQL dengan menjalankan skrip yang ada di file `DDL.sql`.

## Penggunaan Aplikasi

Compile file `App.java` pada aplikasi compiler anda. Ketika aplikasi berjalan, akan muncul pilihan menu. Pilih salah satu opsi menu dengan memasukkan nomor yang sesuai dan tekan Enter. Berikut adalah penjelasan singkat tentang setiap opsi:

1. **Insert Transaction**: Masukkan data transaksi baru ke dalam database.
2. **Update Transaction**: Perbarui data transaksi yang sudah ada.
3. **Show Branch Code**: Menampilkan semua kode cabang warung.
4. **Show Product**: Menampilkan semua produk yang tersedia.
5. **Show Transaction**: Menampilkan data transaksi tertentu berdasarkan nomor struk (bill).
6. **Show All Transaction**: Menampilkan semua data transaksi yang ada di database.
7. **Exit**: Keluar dari aplikasi.
