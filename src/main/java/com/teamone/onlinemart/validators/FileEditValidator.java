/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Enkhbayar
 */
@FacesValidator("FileEditValidator")
public class FileEditValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent uiComponent,
            Object value) throws ValidatorException {

        Part part = (Part) value;

        // 1. validate file name length
        String fileName = getFileName(part);
        if (fileName.length() != 0) {
//            FacesMessage message = new FacesMessage("Error: File is required !!");
//            throw new ValidatorException(message);
            if (!(part.getContentType().startsWith("image"))) {
                FacesMessage message = new FacesMessage("Error: File type is invalid !!");
                throw new ValidatorException(message);
            }

            // 3. validate file size (should not be greater than 2 megabytes)
            if (part.getSize() > 2048000) {
                FacesMessage message = new FacesMessage("Error: File size is too big !!");
                throw new ValidatorException(message);
            }
        }

		// 2. validate file type (only text files allowed)                
//		if (!"text/plain".equals(part.getContentType())) {
    }

    // Extract file name from content-disposition header of file part
    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return "";
    }
}
