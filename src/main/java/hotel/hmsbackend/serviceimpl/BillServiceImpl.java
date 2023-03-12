package hotel.hmsbackend.serviceimpl;

import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.dao.BillDao;
import hotel.hmsbackend.jwt.JwtFilter;
import hotel.hmsbackend.pojo.Bill;
import hotel.hmsbackend.service.BillService;
import hotel.hmsbackend.utils.HMSUtilits;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Slf4j
public class BillServiceImpl implements BillService {
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("generateReport");
        try {
            String fileName;
            if (validateRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = HMSUtilits.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }
                String data = "Name: " + requestMap.get("name") + "\n" + "Contact Number: " + requestMap.get("contactNumber") +
                        "\n" + "Email: " + requestMap.get("email") + "\n" + "Payment Method: " + requestMap.get("paymentMethod");

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(HMSConstant.stored_location + "\\" + fileName + ".pdf"));

                //doc.....
                document.open();
                setRectangleInPdf(document);
                Paragraph paragr = new Paragraph("Bill", getFont("Header"));
                paragr.setAlignment(Element.ALIGN_CENTER);
                document.add(paragr);
                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(paragraph);
                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = HMSUtilits.getJsonArrayFromString((String) requestMap.get("productDetails"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, HMSUtilits.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total : " + requestMap.get("total") + "\n" + "Thank Your for Visiting Us, See you Soon", getFont("Data"));
                document.add(footer);
                document.close();

                return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);

            }
            return HMSUtilits.getResponseEntity("Data Not Found", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addRows(PdfPTable table, Map<String, Object> mapFromJson) {
        log.info("addrows................");
        table.addCell((String) mapFromJson.get("name"));
        table.addCell((String) mapFromJson.get("category"));
        table.addCell((String) mapFromJson.get("qty"));
        table.addCell(Double.toString((Double) mapFromJson.get("price")));
        table.addCell(Double.toString((Double) mapFromJson.get("total")));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("TableHeader ................");
        Stream.of("Name", "Category", "QTY", "Price", "Total", "Discount")
                .forEach(columnHeader -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnHeader));
                    header.setBackgroundColor(BaseColor.ORANGE);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

    }

    private Font getFont(String header) {
        log.info("getfont-------");
        switch (header) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 20, BaseColor.RED);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("inside documentation");
        Rectangle rectangle = new Rectangle(500, 800, 20, 20);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBackgroundColor(BaseColor.BLUE);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        document.add(rectangle);
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetail") &&
                requestMap.containsKey("total");
    }

    private void insertBill(Map<String, Object> requestMap) {
        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("total")));
            bill.setProductDetail((String) requestMap.get("setProductDetail"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());
            billDao.save(bill);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public ResponseEntity<List<Bill>> getAllBills() {
     List<Bill> list = new ArrayList<>();
     if (jwtFilter.isAdmin()){
        list = billDao.getAllBills();
     }else {
            list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
     }
     return  new ResponseEntity<>(list, HttpStatus.OK);
    }
}
