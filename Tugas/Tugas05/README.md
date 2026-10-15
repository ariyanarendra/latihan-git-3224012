# Tugas 05 - Black-box Testing

Nama: Ariya Narendra  
NIM: 3224012  
Mata kuliah: Implementasi Pengujian Perangkat Lunak

Folder ini memuat jawaban Pertemuan 5 tentang:

- Equivalence Partitioning (EP);
- Boundary Value Analysis (BVA);
- kombinasi EP dan BVA;
- pengujian API `POST /api/v1/products`;
- studi kasus terpadu sebagai bagian bonus.

## Di mana testing dilakukan?

Testing dipraktikkan langsung di repository ini:

- `src/main/java/com/ariya/tugas05`: sistem simulasi yang mengikuti aturan pada soal;
- `src/test/java/com/ariya/tugas05`: test case JUnit;
- `.github/workflows/java-unit-test.yml`: menjalankan test otomatis di GitHub Actions.

Alurnya:

1. Test memberikan input, misalnya username `ari1`.
2. Expected output-nya `INVALID` karena panjangnya hanya 4 karakter.
3. JUnit menjalankan input itu pada sistem simulasi.
4. Sistem menghasilkan actual output `INVALID`.
5. Karena expected sama dengan actual, hasil test adalah `PASS`.

`PASS` tidak selalu berarti input valid. `PASS` berarti respons sistem sesuai dengan hasil yang diharapkan.

## Menjalankan testing

```bash
cd Tugas/Tugas05
mvn test
```

Suite Tugas 05 berisi 121 test otomatis untuk Bagian A sampai E.

## Dokumen jawaban

Jawaban tabel lengkap tersedia pada PDF berikut:

[Buka PDF Tugas 05](./Tugas05_BlackBox_3224012_Ariya_Narendra.pdf)

Dokumen mencakup Bagian A sampai D (100 poin) dan Bagian E (bonus 10 poin).
