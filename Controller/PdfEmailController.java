//package com.example.Event.Management.Controller;
//
//
//import java.io.IOException;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.Event.Management.Service.AllEventService;
//import com.example.Event.Management.Service.EmailService;
//
//@RestController
//@RequestMapping("/events")
//public class PdfEmailController {
//
//    @Autowired
//    private AllEventService eventService;
//
////    @Autowired
////    private PdfService pdfService;
//
//    @Autowired
//    private EmailService emailService;
//
////    @GetMapping("/{id}/pdf")
////    public ResponseEntity<ByteArrayResource> generateEventPdf(@PathVariable Long id) throws DocumentException, IOException {
////        Optional<Event> event = eventService.getEventById(id);
////        if (event.isPresent()) {
////            byte[] pdfBytes = pdfService.generateEventPdf(event.get());
////            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
////
////            return ResponseEntity.ok()
////                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=event.pdf")
////                    .contentType(MediaType.APPLICATION_PDF)
////                    .body(resource);
////        }
////        return ResponseEntity.notFound().build();
////    }
//
//    @PostMapping("/{id}/email")
//    public ResponseEntity<Void> shareEventViaEmail(@PathVariable Long id, @RequestParam String to) throws DocumentException, IOException, MessagingException {
//        Optional<Event> event = eventService.getEventById(id);
//        if (event.isPresent()) {
//            byte[] pdfBytes = pdfService.generateEventPdf(event.get());
//            emailService.sendEventEmail(to, "Event Details", "Please find the attached event details.", pdfBytes);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
//}
