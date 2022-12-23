
import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class main {
    public static void main(String[] args) throws IOException {
        String userInput;
        Scanner terminalInput = new Scanner(System.in);
        boolean isLanjut = true;


        //melakukan perulangan
        while (isLanjut) {
            //clearscreen
            clearScreen();
            //main menu

            System.out.println(" ========================= ");
            System.out.println("    LAUNDRY MANAGEMENT     ");
            System.out.println(" ========================= ");
            System.out.println(" 1. Buat Pesanan Baru ");
            System.out.println(" 2. Daftar Pelanggan  ");
            System.out.println(" 3. Hapus Data        ");
            System.out.println(" 4. Restock           ");
            System.out.println(" ========================= ");
            System.out.print(" Masukkan Angka 1-4 : ");
            userInput = terminalInput.next();
            //perkondisian navigasi
            switch (userInput) {
                //sudah aman
                case "1":
                    System.out.println(" ========================= ");
                    System.out.println("      BUAT PESANAN BARU    ");
                    System.out.println(" ========================= ");
                    BuatPesananBaru();
                    RincianBelanja();
                    break;
                //sudah aman
                case "2":
                    System.out.println(" ========================= ");
                    System.out.println("      DAFTAR PELANGGAN     ");
                    System.out.println(" ========================= ");
                    percobaanPelanggan();
                    pilihPelanggan();
                    RincianBelanja();
                    break;
                // aman tapi malah kehabpus semua datanya lol.
                case "3":
                    System.out.println(" ========================= ");
                    System.out.println("          HAPUS DATA       ");
                    System.out.println(" ========================= ");
                    percobaanPelanggan();
                    RecordDelete();
                    break;
                case "4":
                    System.out.println(" ========================= ");
                    System.out.println("           RESTOCK         ");
                    System.out.println(" ========================= ");
                    tampilkanStockBarang();
                    Updatestock();

                    break;
                default:
                    System.out.println(" Masukkan Anda tidak valid !");
                    break;
            }
            isLanjut = getYesorNo("Apakah anda ingin mengulang kembali? ");

        }
    }


    private  static void hapusDP() throws  IOException{
        File database = new File("databasePelanggan.txt");
        FileReader fileinput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileinput);

        File tempDB = new File("tempDBPelanggan.txt");
        FileWriter fileoutput = new FileWriter(tempDB);
        BufferedWriter bufferOutput = new BufferedWriter(fileoutput);


        Scanner terminalInput = new Scanner(System.in);
        System.out.print(" Masukkan Nomor data pelanggan : ");
        int deleteNum = terminalInput.nextInt();


        int entryCounts = 0;
        String data = bufferInput.readLine();

        while(data != null){
            entryCounts++;
            boolean isDelete = false;
            StringTokenizer st = new StringTokenizer(data,",");

            if (deleteNum == entryCounts){
                System.out.println(" ======================  ");
                System.out.println(" Data yang akan dihapus  ");
                System.out.println(" ======================  ");
                System.out.println(data);
                isDelete = getYesorNo(" Apakah anda ingin menghapus data pelanggan ? ");

            }
            if (isDelete){
                System.out.println("Data Berhasil dihapus");
            } else{
                bufferOutput.write(data);
                bufferOutput.newLine();
            }
            data = bufferInput.readLine();
        }

        database.delete();
        tempDB.renameTo(database);


    }

    private static void RecordDelete() throws IOException{
        Scanner tempInput = new Scanner(System.in);
        String hapuspelanggan, record;
        File tempDB = new File("databasePelanggan.txt");
        File database = new File("tempDBPelanggan.txt");

        BufferedReader br = new BufferedReader(new FileReader(tempDB));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempDB));

        System.out.println(" ========================== ");
        System.out.println("     HAPUS DATA PELANGGAN   ");
        System.out.println(" ========================== ");

        System.out.print(" Pilih nomor pelanggan : ");
        hapuspelanggan = tempInput.nextLine();

        hapuspelanggan= br.readLine();

        int entryCounts = 0;
        while((record = br.readLine()) != null){
            entryCounts++;
            StringTokenizer st = new StringTokenizer(record,",");
            if (record.contains(hapuspelanggan))
                continue;
            bw.write(record);
            bw.flush();
            bw.newLine();
        }
        br.close();
        bw.close();
        tempDB.delete();
        database.renameTo(tempDB);


    }

    private static void Updatestock() throws  IOException{
        System.out.println(" =========================== ");;
        System.out.println("         Update Stock        ");
        System.out.println(" =========================== ");

        File databasePelanggan = new File("databaseStock.txt");
        FileReader fileInput = new FileReader(databasePelanggan);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        File tempDBPelanggan = new File ("tempDBPelanggan.txt");
        FileWriter fileOutput = new FileWriter(tempDBPelanggan);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);

        Scanner terminalInput = new Scanner(System.in);
        System.out.print(" Masukkan nomor data yang akan di Restock : ");
        int updateNum = terminalInput.nextInt();

        String data = bufferInput.readLine();
        int entrycount= 0;

        while (data != null){
            entrycount++;
            StringTokenizer st = new StringTokenizer(data,",");

            if (updateNum == entrycount) {
                System.out.println(" Data yang akan di RESTOCK adalah : ");
                System.out.println("====================================");
                st.nextToken();
                System.out.println(" Nama : " + st.nextToken());
                System.out.println(" Jumlah Stock : " + st.nextToken());

                String[] fieldData = {"stock"};
                String[] tempData = new String[1];

                st = new StringTokenizer(data, ",");
                String originalData = st.nextToken();

                for (int i = 0; i < fieldData.length; i++) {
                    boolean isUpdate = getYesorNo(" Apakah anda ingin merubah Stock? ");

                    originalData = st.nextToken();
                    if (isUpdate) {
                        terminalInput = new Scanner(System.in);
                        System.out.println(" Masukkan Jumlah Stock : ");
                        tempData[i] = terminalInput.nextLine();
                    } else {
                        tempData[i] = fieldData[i];
                    }
                }

                System.out.println("DATA BERHASIL DI UBAH");
            }
            bufferOutput.newLine();
            data = bufferInput.readLine();
        }

        bufferOutput.flush();
        databasePelanggan.delete();
        tempDBPelanggan.renameTo(databasePelanggan);




    }
    private static void RincianBelanja() throws IOException {

        Scanner terminalInput = new Scanner(System.in);
        DecimalFormat decimalFormat = new DecimalFormat();
        int koinlaundry, scentBoster, deterjen, softener;
        boolean isLanjut = true;
        System.out.println(" ============================ ");
        System.out.println("       RINCIAN BELANJA        ");
        System.out.println(" ============================ ");
        tampilkanStockBarang();

        System.out.print("Masukkan Jumlah Koin Laundry (Rp.25.000/pc) : ");
        koinlaundry = terminalInput.nextInt();
        System.out.print("Masukkan Jumlah Deterjen (Rp. 5.000/pc) : ");
        deterjen = terminalInput.nextInt();
        System.out.print("Masukkan Jumlah Softener (Rp. 7.000/pc) : ");
        softener = terminalInput.nextInt();
        System.out.print("Masukkan Jumlah Scent Booster (Rp.10.000/pc) : ");
        scentBoster = terminalInput.nextInt();


        double hargatotal;
        hargatotal = ((koinlaundry * 25000) + (deterjen * 5000) + (softener * 7000) + (scentBoster * 10000));
        System.out.println("Total Belanja : Rp. " + decimalFormat.format(hargatotal));

        isLanjut = getYesorNo("Cetak Invoice ? ");

        if (isLanjut){
            System.out.println(" ================= ");
            System.out.println("  INVOICE DICETAK  ");
            System.out.println(" ================= ");
            System.out.println(" Koin Laundry : " + koinlaundry);
            System.out.println(" Deterjen : " + deterjen);
            System.out.println(" Softerner : " + softener);
            System.out.println(" Scent Booster : " + scentBoster);
            System.out.println(" ------------------------------ ");
            System.out.println(" Total Harga : " + decimalFormat.format(hargatotal));

        } else {
            System.out.println("  PROSES SELESAI   ");
        }


}
    private static  void BuatPesananBaru() throws IOException {

        FileWriter fileoutput = new FileWriter("databasePelanggan.txt", true);
        BufferedWriter bufferOutput = new BufferedWriter(fileoutput);

        //mengmabil input dari user
        Scanner terminalInput = new Scanner(System.in);

        String nama, nomorseluler, alamat,tahun;
        System.out.print(" Masukkan Nama Lengkap : ");
        nama = terminalInput.nextLine();
        System.out.print(" Masukkan Nomor Seluler : ");
        nomorseluler = terminalInput.nextLine();
        System.out.print(" Masukkan Alamat : ");
        alamat = terminalInput.nextLine();


        String[] keywords = {nama+","+nomorseluler+","+ alamat+""};
        System.out.println(Arrays.toString(keywords));


        System.out.println("==========================");
        System.out.println("Nama : "+ nama);
        System.out.println("Nomor Seluler : " +nomorseluler);
        System.out.println("Alamat : " + alamat);
        System.out.println(" ==========================");

        boolean tambahDataPelanggan = true;
        tambahDataPelanggan = getYesorNo("Apakah ingin menambahkan ke Database ? ");

       boolean isTambah = true;
        if(isTambah){
            bufferOutput.write(   nama + "," + nomorseluler + "," + alamat);
            bufferOutput.newLine();
            bufferOutput.flush();
        }

        // menulis buku di databse
        bufferOutput.close();
    }

    /*private static String ambilTahun() throws IOException{
        boolean tahunValid = false;
        Scanner terminalInput = new Scanner(System.in);
        String tahunInput = terminalInput.nextLine();
        while(!tahunValid){
            try {
                Year.parse(tahunInput);
                tahunValid = true;
            } catch (Exception e){
                System.out.println(" Format Tahun salah !");
                System.out.println("Silahkan masukkan tahun  : ");
                tahunValid = false;
                tahunInput = terminalInput.nextLine();
            }
        }
        return tahunInput;
    } */
    private static void tampilkanDaftarPelanggan() throws IOException{
        FileReader fileInput = new FileReader("databasePelanggan") ;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("databasePelanggan.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            tampilkanDaftarPelanggan();
            return;
        }
        System.out.println("\n  [ No ]  \t [NAMA]           \t [Nomor Selular]           [Alamat]");
        System.out.println(" ===================================================================== ");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null) {
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("  |%2d", nomorData);
            System.out.printf(" |\t%s", stringToken.nextToken());
            System.out.printf(" |\t%s", stringToken.nextToken());
            System.out.printf(" |\t%s", stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();
        }
        System.out.println(" ===================================================================== ");

    }
    private static  void tampilkanStockBarang() throws IOException{
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("databaseStock.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahoeloe");
            tampilkanStockBarang();
            return;
        }
        System.out.println("\n  [ No ]  \t [NAMA BARANG]           \t [JUMLAH TERSISA]        ");
        System.out.println(" ===================================================================== ");
        String dataStock = bufferInput.readLine();
        int nomorDataStock = 0;
        int koinlaundry = 10;
        while (dataStock != null){
            nomorDataStock++;
            StringTokenizer stringToken = new StringTokenizer(dataStock, ",");

            stringToken.nextToken();
            System.out.printf("  |%2d|", nomorDataStock);
            System.out.printf("\t|\t%s|", stringToken.nextToken());
            System.out.printf("\t |\t%s|", stringToken.nextToken());
            System.out.print("\n");

            dataStock = bufferInput.readLine();
        }
        System.out.println("====================================================================== ");


    }
    private static boolean getYesorNo(String message){
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n"+message+" (y/n)? ");
        String pilihanUser = terminalInput.next();

        while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
            System.err.println("Pilihan anda bukan y atau n");
            System.out.print("\n"+message+" (y/n)? ");
            pilihanUser = terminalInput.next();
        }

        return pilihanUser.equalsIgnoreCase("y");

    }
    private static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex){
            System.err.println("tidak bisa clear screen");
        }
    }

    private static void percobaanPelanggan() throws IOException{
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("databasePelanggan.txt");
            bufferInput = new BufferedReader(fileInput);

        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahoeloe");
            return;
        }
        System.out.println("\n|  NO  |        \tNAMA PELANGGAN           |           \tNOMOR SELULER                |        \tALAMAT        |");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null){
            nomorData++;

            StringTokenizer stringToken = new StringTokenizer(data,",");
            System.out.printf("| %2d  ", nomorData);
            System.out.printf("|       \t %-20s  ", stringToken.nextToken());
            System.out.printf("|           \t %-20s     ", stringToken.nextToken());
            System.out.printf("|            \t %-20s         ", stringToken.nextToken());
            System.out.print("\n");
            data = bufferInput.readLine();
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------");

    }
    private static void pilihPelanggan() throws  IOException{
        File database = new File("databasePelanggan.txt");
        FileReader fileinput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileinput);

        Scanner terminalInput = new Scanner(System.in);
        System.out.println(" ============================    ");
        System.out.println(" Masukkan Nomor data pelanggan : ");
        System.out.println(" ============================    ");
        int pilihNum = terminalInput.nextInt();

        int entryCounts = 0;
        String data = bufferInput.readLine();
        while(data != null) {
            entryCounts++;
            boolean isDelete = false;
            StringTokenizer st = new StringTokenizer(data, ",");

            if (pilihNum == entryCounts) {
                System.out.println(" =====================    ");
                System.out.println("     Data Pelanggan       ");
                System.out.println(" ======================   ");
                System.out.println(data);
            }
            data = bufferInput.readLine();
        }

    }

}