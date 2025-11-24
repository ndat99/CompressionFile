package model.bo;

import model.dao.JobDAO;
import model.bean.CompressionJob;
import java.io.*;
import java.util.zip.*;

public class BackgroundWorker implements Runnable { //để chạy đc trong thread
    private boolean isRunning = true;
    private JobDAO jobDAO = new JobDAO(); 

    public void stop() {
    	isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                CompressionJob job = jobDAO.getPendingJob();
                if (job != null) {                    
                    //đường dẫn file upload
                    String sourcePath = jobDAO.getFilePath(job.getId());
                    if (sourcePath != null) {
                        //file zip kết quả sẽ nằm cùng cấp với thư mục/file gốc
                        String zipFile = sourcePath + ".zip";
                        jobDAO.updateJobStatus(job.getId(), "PROCESSING", null);

                        //Nén folder vừa upload lên thành file zip
                        performZip(sourcePath, zipFile);
                        Thread.sleep(2000); //cho sleep khớp với thời gian refresh trang để vẫn hiện đủ trạng thái PROCESSING
                        jobDAO.updateJobStatus(job.getId(), "COMPLETED", zipFile);
                    }
                } else {
                	//nếu ko có job nào thì nghỉ 2s rồi kiểm tra lại chứ ko là chết CPU
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                	Thread.sleep(5000); //nếu lỗi thì nghỉ 5s rồi chạy lại, tránh spam
                } catch (InterruptedException ex) {}
            }
        }
    }

    //nén file hoặc thư mục thành file zip 
    private void performZip(String sourcePath, String zipPath) throws IOException {
        File sourceFile = new File(sourcePath);
        try (FileOutputStream fos = new FileOutputStream(zipPath);
           //class tạo file zip
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

        	//nếu là thư mục thì nén tất cả file bên trong
            if (sourceFile.isDirectory()) {
                File[] files = sourceFile.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) { //chỉ nén file, bỏ qua thư mục con
                            addToZip(file, zipOut);
                        }
                    }
                }
            } else {
                //nếu là file lẻ thì nén như bth
                addToZip(sourceFile, zipOut);
            }
        }
    }

    //thêm 1 file vào zip
    private void addToZip(File file, ZipOutputStream zipOut) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            //ZipEntry là tên file nằm bên trong file zip
            ZipEntry zipEntry = new ZipEntry(file.getName());
            	//tạo file con trong zip
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            				//đọc file gốc
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length); //ghi vào file zip
            }
            zipOut.closeEntry();
        }
    }
}