package com.bodegahub.sistema_bodega.service;

import com.bodegahub.sistema_bodega.model.Contacto;
import com.bodegahub.sistema_bodega.repository.ContactoRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;

@Service
public class ContactoService {

    private static final Logger logger = LoggerFactory.getLogger(ContactoService.class);

    private final ContactoRepository contactoRepository;
    private final JavaMailSender mailSender;

    public ContactoService(ContactoRepository contactoRepository, JavaMailSender mailSender) {
        this.contactoRepository = contactoRepository;
        this.mailSender = mailSender;
    }

    public Contacto guardarContacto(Contacto contacto) {
        Contacto contactoGuardado = contactoRepository.save(contacto);

        // Enviar email de forma asíncrona para no bloquear la respuesta
        try {
            enviarEmailDeNotificacion(contacto);
        } catch (Exception e) {
            logger.error("Error al enviar email de notificación", e);
            // No lanzamos la excepción para no afectar la respuesta al cliente
        }

        return contactoGuardado;
    }

    private void enviarEmailDeNotificacion(Contacto contacto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo("destino@tuempresa.com"); // Cambia por el email destino
            helper.setSubject("📧 Nuevo Contacto - " + contacto.getNombre_bodega());
            helper.setFrom("noreply@bodegahub.com");

            String htmlContent = construirContenidoEmail(contacto);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            logger.info("Email de notificación enviado exitosamente");

        } catch (MessagingException e) {
            logger.error("Error al crear el email de notificación", e);
            throw new RuntimeException("Error al enviar email", e);
        }
    }

    private String construirContenidoEmail(Contacto contacto) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #8B0000; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; background: #f9f9f9; }
                    .field { margin-bottom: 10px; }
                    .label { font-weight: bold; color: #8B0000; }
                    .message-box { background: white; padding: 15px; border-left: 4px solid #8B0000; margin: 15px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🆕 Nuevo Mensaje de Contacto</h1>
                    </div>
                    <div class="content">
                        <div class="field">
                            <span class="label">Nombre:</span> %s %s
                        </div>
                        <div class="field">
                            <span class="label">Email:</span> %s
                        </div>
                        <div class="field">
                            <span class="label">Teléfono:</span> %s
                        </div>
                        <div class="field">
                            <span class="label">Bodega:</span> %s
                        </div>
                        <div class="field">
                            <span class="label">Ciudad:</span> %s
                        </div>
                        <div class="field">
                            <span class="label">Dirección:</span> %s
                        </div>
                        <div class="field">
                            <span class="label">Fecha:</span> %s
                        </div>
                        <div class="message-box">
                            <strong>Mensaje:</strong><br>
                            %s
                        </div>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                contacto.getNombre(),
                contacto.getApellido(),
                contacto.getCorreo(),
                contacto.getTelefono(),
                contacto.getNombre_bodega(),
                contacto.getCiudad(),
                contacto.getDireccion(),
                contacto.getFecha().toString(),
                contacto.getMensaje()
        );
    }

    public List<Contacto> listarContactos() {
        return contactoRepository.findAll();
    }
}