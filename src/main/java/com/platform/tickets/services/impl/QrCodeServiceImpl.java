package com.platform.tickets.services.impl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.platform.tickets.domain.entities.QrCode;
import com.platform.tickets.domain.entities.Ticket;
import com.platform.tickets.domain.entities.QrCodeStatusEnum;
import com.platform.tickets.exceptions.QrCodeGenerationException;
import com.platform.tickets.exceptions.QrCodeNotFoundException;
import com.platform.tickets.repositories.QrCodeRepository;
import com.platform.tickets.services.QrCodeService;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public QrCode generateQrCode(Ticket ticket) {
        try {
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateQrCodeImage(uniqueId);
            QrCode qrCode = new QrCode();
            qrCode.setId(uniqueId);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
            qrCode.setValue(qrCodeImage);
            qrCode.setTicket(ticket);
            return qrCodeRepository.saveAndFlush(qrCode);
        }catch (WriterException | IOException e){
            throw new QrCodeGenerationException("Failed to generate Qr Code");
        }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId,userId)
                .orElseThrow(QrCodeNotFoundException::new);

        try {
           return Base64.getDecoder().decode(qrCode.getValue());
        }catch (IllegalArgumentException e){
            log.error("Invalid base64 qr code for ticketID: {}",ticketId,e);
            throw new QrCodeNotFoundException();
        }
    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {

        BitMatrix bitMatrix = qrCodeWriter.encode(uniqueId.toString(), BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(qrCodeImage,"PNG",baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}