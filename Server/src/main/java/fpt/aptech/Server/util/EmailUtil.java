/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package fpt.aptech.Server.util;

import fpt.aptech.Server.entities.OrderDetails;
import fpt.aptech.Server.entities.Orders;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class EmailUtil {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendSetPasswordEmail(String email, String token) throws MessagingException {
        String htmlContent = """
<!doctype html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Set Password</title>
    <style media="all" type="text/css">
    /* -------------------------------------
    GLOBAL RESETS
------------------------------------- */
    body {
      font-family: Helvetica, sans-serif;
      -webkit-font-smoothing: antialiased;
      font-size: 16px;
      line-height: 1.3;
      -ms-text-size-adjust: 100%%;
      -webkit-text-size-adjust: 100%%;
    }
    table {
      border-collapse: separate;
      mso-table-lspace: 0pt;
      mso-table-rspace: 0pt;
      width: 100%%;
    }
    table td {
      font-family: Helvetica, sans-serif;
      font-size: 16px;
      vertical-align: top;
    }
    /* -------------------------------------
    BODY & CONTAINER
------------------------------------- */
    body {
      background-color: #f4f5f6;
      margin: 0;
      padding: 0;
    }
    .body {
      background-color: #f4f5f6;
      width: 100%%;
    }
    .container {
      margin: 0 auto !important;
      max-width: 600px;
      padding: 0;
      padding-top: 24px;
      width: 600px;
    }
    .content {
      box-sizing: border-box;
      display: block;
      margin: 0 auto;
      max-width: 600px;
      padding: 0;
    }
    /* -------------------------------------
    HEADER, FOOTER, MAIN
------------------------------------- */
    .main {
      background: #ffffff;
      border: 1px solid #eaebed;
      border-radius: 16px;
      width: 100%%;
    }
    .wrapper {
      box-sizing: border-box;
      padding: 24px;
    }
    .footer {
      clear: both;
      padding-top: 24px;
      text-align: center;
      width: 100%%;
    }
    .footer td,
    .footer p,
    .footer span,
    .footer a {
      color: #9a9ea6;
      font-size: 16px;
      text-align: center;
    }
    /* -------------------------------------
    TYPOGRAPHY
------------------------------------- */
    p {
      font-family: Helvetica, sans-serif;
      font-size: 16px;
      font-weight: normal;
      margin: 0;
      margin-bottom: 16px;
    }
    a {
      color: #0867ec;
      text-decoration: underline;
    }
    /* -------------------------------------
    BUTTONS
------------------------------------- */
    .btn {
      box-sizing: border-box;
      min-width: 100%% !important;
      width: 100%%;
    }
    .btn > tbody > tr > td {
      padding-bottom: 16px;
    }
    .btn table {
      width: auto;
    }
    .btn table td {
      background-color: #ffffff;
      border-radius: 4px;
      text-align: center;
    }
    .btn a {
      background-color: #ffffff;
      border: solid 2px #0867ec;
      border-radius: 4px;
      box-sizing: border-box;
      color: #0867ec;
      cursor: pointer;
      display: inline-block;
      font-size: 16px;
      font-weight: bold;
      margin: 0;
      padding: 12px 24px;
      text-decoration: none;
      text-transform: capitalize;
    }
    .btn-primary table td {
      background-color: #0867ec;
    }
    .btn-primary a {
      background-color: #0867ec;
      border-color: #0867ec;
      color: #ffffff;
    }
    @media all {
      .btn-primary table td:hover {
        background-color: #ec0867 !important;
      }
      .btn-primary a:hover {
        background-color: #ec0867 !important;
        border-color: #ec0867 !important;
      }
    }
    /* -------------------------------------
    OTHER STYLES THAT MIGHT BE USEFUL
------------------------------------- */
    .last {
      margin-bottom: 0;
    }
    .first {
      margin-top: 0;
    }
    .align-center {
      text-align: center;
    }
    .align-right {
      text-align: right;
    }
    .align-left {
      text-align: left;
    }
    .text-link {
      color: #0867ec !important;
      text-decoration: underline !important;
    }
    .clear {
      clear: both;
    }
    .mt0 {
      margin-top: 0;
    }
    .mb0 {
      margin-bottom: 0;
    }
    .preheader {
      color: transparent;
      display: none;
      height: 0;
      max-height: 0;
      max-width: 0;
      opacity: 0;
      overflow: hidden;
      mso-hide: all;
      visibility: hidden;
      width: 0;
    }
    .powered-by a {
      text-decoration: none;
    }
    /* -------------------------------------
    RESPONSIVE AND MOBILE FRIENDLY STYLES
------------------------------------- */
    @media only screen and (max-width: 640px) {
      .main p,
      .main td,
      .main span {
        font-size: 16px !important;
      }
      .wrapper {
        padding: 8px !important;
      }
      .content {
        padding: 0 !important;
      }
      .container {
        padding: 0 !important;
        padding-top: 8px !important;
        width: 100%% !important;
      }
      .main {
        border-left-width: 0 !important;
        border-radius: 0 !important;
        border-right-width: 0 !important;
      }
      .btn table {
        max-width: 100%% !important;
        width: 100%% !important;
      }
      .btn a {
        font-size: 16px !important;
        max-width: 100%% !important;
        width: 100%% !important;
      }
    }
    /* -------------------------------------
    PRESERVE THESE STYLES IN THE HEAD
------------------------------------- */
    @media all {
      .ExternalClass {
        width: 100%%;
      }
      .ExternalClass,
      .ExternalClass p,
      .ExternalClass span,
      .ExternalClass font,
      .ExternalClass td,
      .ExternalClass div {
        line-height: 100%%;
      }
      .apple-link a {
        color: inherit !important;
        font-family: inherit !important;
        font-size: inherit !important;
        font-weight: inherit !important;
        line-height: inherit !important;
        text-decoration: none !important;
      }
      #MessageViewBody a {
        color: inherit;
        text-decoration: none;
        font-size: inherit;
        font-family: inherit;
        font-weight: inherit;
        line-height: inherit;
      }
    }
    </style>
  </head>
  <body>
    <table role="presentation" border="0" cellpadding="0" cellspacing="0" class="body">
      <tr>
        <td>&nbsp;</td>
        <td class="container">
          <div class="content">

            <!-- START CENTERED WHITE CONTAINER -->
            <span class="preheader">This is preheader text. Some clients will show this text as a preview.</span>
            <table role="presentation" border="0" cellpadding="0" cellspacing="0" class="main">

              <!-- START MAIN CONTENT AREA -->
              <tr>
                <td class="wrapper">
                  <p>Hi there</p>
                  <p>We received a request to reset the password for your account. Click the button below to reset your password:</p>
                  <table role="presentation" border="0" cellpadding="0" cellspacing="0" class="btn btn-primary">
                    <tbody>
                      <tr>
                        <td align="left">
                          <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                            <tbody>
                              <tr>
                                <td> <a href="http://localhost:8888/user/set-password?token=%s" target="_blank">Click link to reset-password</a> </td>
                              </tr>
                            </tbody>
                          </table>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                  <p>If you did not request a password reset, please ignore this email or contact support if you have questions.</p>
                  <p>Thanks,<br>HinSan Team</p>
                </td>
              </tr>

              <!-- END MAIN CONTENT AREA -->
              </table>

            <!-- START FOOTER -->
            <div class="footer">
              <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="content-block">
                    <span class="apple-link">Company Inc, 7-11 Commercial Ct, Belfast BT1 2NB</span>
                    <br> Don't like these emails? <a href="http://htmlemail.io/blog">Unsubscribe</a>.
                  </td>
                </tr>
                <tr>
                  <td class="content-block powered-by">
                    Powered by <a href="http://htmlemail.io">HinSan Team</a>
                  </td>
                </tr>
              </table>
            </div>

            <!-- END FOOTER -->
            
<!-- END CENTERED WHITE CONTAINER --></div>
        </td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </body>
</html>
""";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set Password");
        mimeMessageHelper.setText(String.format(htmlContent, token), true);
        javaMailSender.send(mimeMessage);
    }

    public void sendSuccessOrders(String email, Orders orders, List<OrderDetails> orderDetails) throws MessagingException {
        String htmlContent = """
                              <html lang="en">
                                         <head>
                                             <meta charset="utf-8">
                                             <title>Bill Details</title>
                                             <meta name="viewport" content="width=device-width, initial-scale=1">
                                             <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
                                             <style type="text/css">
                                                 body {
                                                     margin-top: 20px;
                                                     background-color: #eee;
                                                 }
                                                 .card {
                                                     box-shadow: 0 20px 27px 0 rgb(0 0 0 / 5%);
                                                 }
                                                 .card {
                                                     position: relative;
                                                     display: flex;
                                                     flex-direction: column;
                                                     min-width: 0;
                                                     word-wrap: break-word;
                                                     background-color: #fff;
                                                     background-clip: border-box;
                                                     border: 0 solid rgba(0, 0, 0, .125);
                                                     border-radius: 1rem;
                                                 }
                                                 .card-body{
                                                     padding: 10rem 7rem;
                                                 }
                                             </style>
                                         </head>
                                         <body>
                                             <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css" 
                                                   integrity="sha256-2XFplPlrFClt0bIdPgpz8H7ojnk10H69xRqd9+uTShA=" crossorigin="anonymous" />
                                             <div class="container" id="invoice">
                                                 <div class="row">
                                                     <div class="col-lg-12">
                                                         <div class="card">
                                                             <div class="card-body">
                                                                 <div class="invoice-title">
                                                                     <h4 class="float-end font-size-15">Invoice #DS0204 <span 
                                                                         class="badge bg-success font-size-12 ms-2">Paid</span></h4>
                                                                     <div class="mb-4">
                                                                         <h2 class="mb-1 text-muted">HinSa.com</h2>
                                                                     </div>
                                                                     <div class="text-muted" style="font-size: 2rem;">
                                                                         <p class="mb-1">3184 Spruce Drive Pittsburgh, PA 15201</p>
                                                                         <p class="mb-1"><i class="uil uil-envelope-alt me-1"></i> <a 
                                                                             href="/cdn-cgi/l/email-protection" class="__cf_email__" 
                                                                             data-cfemail="8ff7f6f5cfb6b7b8a1ece0e2">[email&#160;protected]</a></p>
                                                                         <p><i class="uil uil-phone me-1"></i> 012-345-6789</p>
                                                                     </div>
                                                                 </div>
                                                                 <hr class="my-4">
                                                                 <div class="row" style="font-size: 2rem;">
                                                                     <div class="col-sm-6">
                                                                         <div class="text-muted">
                                                                             <h5 class="font-size-16 mb-3">Billed To:</h5>
                                                                             <h5 class="font-size-15 mb-2">\""" + orders.getUserId().getName() + \"""</h5>
                                                                             <p class="mb-1">\""" + orders.getUserId().getAddress() + \"""</p>
                                                                             <p class="mb-1"><a href="/cdn-cgi/l/email-protection" class="__cf_email__" 
                                                                                 data-cfemail="b5e5c7d0c6c1dadbf8dcd9d9d0c7f5d4c7d8ccc6c5cc9bd6dad8">\""" + orders.getUserId().getEmail() + \"""</a></p>
                                                                             <p>\""" + orders.getUserId().getPhone() + \"""</p>
                                                                         </div>
                                                                     </div>
                                                                     <div class="col-sm-6" style="font-size: 2rem;">
                                                                         <div class="text-muted text-sm-end">
                                                                             <div>
                                                                                 <h5 class="font-size-15 mb-1">Invoice No:</h5>
                                                                                 <p>\""" + orders.getId() + \"""</p>
                                                                             </div>
                                                                             <div class="mt-4">
                                                                                 <h5 class="font-size-15 mb-1">Invoice Date:</h5>
                                                                                 <p>\""" + orders.getOrderDate() + \"""</p>
                                                                             </div>
                                                                             <div class="mt-4">
                                                                                 <h5 class="font-size-15 mb-1">Order No:</h5>
                                                                                 <p>\""" + orders.getId() + \"""</p>
                                                                             </div>
                                                                         </div>
                                                                     </div>
                                                                 </div>
                                                                 <div class="py-2" style="font-size: 2rem;">
                                                                     <h5 class="font-size-15">Order Summary</h5>
                                                                     <div class="table-responsive">
                                                                         <table class="table align-middle table-nowrap table-centered mb-0">
                                                                             <thead>
                                                                                 <tr>
                                                                                     <th style="width: 70px;">No.</th>
                                                                                     <th>Item</th>
                                                                                     <th>Price</th>
                                                                                     <th>Quantity</th>
                                                                                     <th class="text-end" style="width: 120px;">Total</th>
                                                                                 </tr>
                                                                             </thead>
                                                                             <tbody>\""" + generateOrderDetailsHtml(orderDetails) + \"""
                                                                                 <tr>
                                                                                     <th scope="row" colspan="4" class="text-end">Sub Total</th>
                                                                                     <td class="text-end">\""" + orders.getTotalPrice() + \"""</td>
                                                                                 </tr>
                                                                                 <tr>
                                                                                     <th scope="row" colspan="4" class="border-0 text-end">
                                                                                         Discount :</th>
                                                                                     <td class="border-0 text-end">- $00.00</td>
                                                                                 </tr>
                                                                                 <tr>
                                                                                     <th scope="row" colspan="4" class="border-0 text-end">
                                                                                         Shipping Charge :</th>
                                                                                     <td class="border-0 text-end">$00.00</td>
                                                                                 </tr>
                                                                                 <tr>
                                                                                     <th scope="row" colspan="4" class="border-0 text-end">
                                                                                         Tax</th>
                                                                                     <td class="border-0 text-end">$00.00</td>
                                                                                 </tr>
                                                                                 <tr>
                                                                                     <th scope="row" colspan="4" class="border-0 text-end">Total</th>
                                                                                     <td class="border-0 text-end">
                                                                                         <h4 style="font-size: 2rem;" class="m-0 fw-semibold">\""" + orders.getTotalPrice() + \"""</h4>
                                                                                     </td>
                                                                                 </tr>
                                                                             </tbody>
                                                                         </table>
                                                                     </div>
                                                                     <div class="d-print-none mt-4">
                                                                         <div class="float-end">
                                                                             <a href="#" id="download" class="btn btn-success me-1"><i 
                                                                                 class="fa fa-print"></i></a>
                                                                             <a href="/user/order-history" class="btn btn-primary w-md">Done</a>
                                                                         </div>
                                                                     </div>
                                                                 </div>
                                                             </div>
                                                         </div>
                                                     </div>
                                                 </div>
                                             </div>
                                             <script data-cfasync="false" src="/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script>
                                             <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
                                             <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
                                             <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.min.js"></script>
                                             <script>
                                                 window.onload = function () {
                                                     document.getElementById("download")
                                                         .addEventListener("click", () => {
                                                             const invoice = this.document.getElementById("invoice");
                                                             var opt = {
                                                                 filename: 'billdetails',
                                                                 image: {type: 'jpeg', quality: 0.98},
                                                                 html2canvas: {scale: 2},
                                                                 jsPDF: {unit: 'in', format: 'letter', orientation: 'portrait'}
                                                             };
                                                             html2pdf().from(invoice).set(opt).save();
                                                         })
                                                 }
                                             </script>
                                         </body>
                                     </html>
""";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Success Orders");
        mimeMessageHelper.setText(String.format(htmlContent), true);
        javaMailSender.send(mimeMessage);
    }

    private String generateOrderDetailsHtml(List<OrderDetails> orderDetails) {
        StringBuilder detailsHtml = new StringBuilder();
        int index = 1;
        for (OrderDetails detail : orderDetails) {
            detailsHtml.append("<tr>")
                    .append("<td>").append(index++).append("</td>")
                    .append("<td>").append(detail.getProductId().getName()).append("</td>")
                    .append("<td>").append(detail.getTotalPrice()).append("</td>")
                    .append("<td>").append(detail.getQuantity()).append("</td>")
                    .append("<td class='text-end'>").append(detail.getTotalPrice() * detail.getQuantity()).append("</td>")
                    .append("</tr>");
        }
        return detailsHtml.toString();
    }
}
