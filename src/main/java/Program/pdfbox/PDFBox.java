package Program.pdfbox;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import Program.config.Constants;

@Component
public class PDFBox {
	/**
	 * create PDF
	 * 
	 * @param text
	 * @param fileName
	 */
	public void createPDFWithText(String text, String fileName) throws IOException {
		PDDocument doc = null;
		PDPage page = null;
		// Add new page
		doc = new PDDocument();
		page = new PDPage();
		doc.addPage(page);
		PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

		PDPageContentStream content = new PDPageContentStream(doc, page);
		content.beginText();
		content.setFont(font, 14);
		content.setNonStrokingColor(Color.BLACK);
		// set space between lines
		content.setLeading(15);
		content.newLineAtOffset(100, 700);
		text = text.replace("\r", " ");
		String[] lines = text.split("\n");
		for (String str : lines) {
			content.newLine();
			content.showText(str);
		}
		content.endText();
		content.close();

		doc.save(fileName + ".pdf");
		doc.close();
	}

	/**
	 * read text from PDF
	 * 
	 * @param filePath
	 * @return
	 */
	public String readPDF(String filePath) throws IOException {
		PDDocument doc;
		String content = null;
		String output = "";
		// Loading an existing document
		File input = new File(filePath);
		doc = Loader.loadPDF(input);

		PDFTextStripper stripper = new PDFTextStripper();
		content = stripper.getText(doc);
		String[] lines = content.split("\\r?\\n");
		for (String line : lines) {
			output += line + "\n";
		}
		return output;
	}

	/**
	 * insert image
	 */
	public void insertImg(String pdf, String img) throws IOException {
		PDDocument doc;
		File input = new File(pdf);
		doc = Loader.loadPDF(input);
		doc.addPage(new PDPage());
		PDPage page = doc.getPage(doc.getNumberOfPages() - 1);
		PDImageXObject pdImage = PDImageXObject.createFromFile(img, doc);
		PDPageContentStream content = new PDPageContentStream(doc, page);
		content.drawImage(pdImage, 70, 250);
		content.close();
		doc.save(pdf);
		content.close();
	}

	/*
	 * Encrypt a PDF
	 */
	public void encryptFile(String pdf, String pass) throws IOException {
		File input = new File(pdf);
		PDDocument doc = Loader.loadPDF(input);
		AccessPermission ap = new AccessPermission();
		StandardProtectionPolicy spp = new StandardProtectionPolicy("admin", pass, ap);
		spp.setEncryptionKeyLength(128);
		spp.setPermissions(ap);
		doc.protect(spp);
		doc.save(pdf);
		doc.close();
	}
	
	/*
	 * Add JavaScript
	 */
	public void addJs(String pdf) throws IOException{
		File input = new File(pdf);
		PDDocument doc = Loader.loadPDF(input);
		String javaScript = "app.alert( {cMsg: 'this is an example', nIcon: 3,"
				   + " nType: 0,cTitle: 'PDFBox Javascript example' } );"; 
		PDActionJavaScript pdajs = new PDActionJavaScript(javaScript);
		doc.getDocumentCatalog().setOpenAction(pdajs);
		doc.save(pdf);
		doc.close();
	}
	
	/*
	 * Split Pages
	 */
	public void splitPages(String pdf, String name) throws IOException{
		File input = new File(pdf);
		PDDocument doc = Loader.loadPDF(input);
		Splitter splitter = new Splitter();
		List<PDDocument> Pages = splitter.split(doc);
		for (int i = 0; i < Pages.size(); i++) {
			Pages.get(i).save(Constants.UPLOAD_DIR + "/" + name + (i+1) + ".pdf");
		}
		doc.close();
	}
	
	/*
	 * Merge Files
	 */
	public void mergeFiles(List<String> source, String destination) throws IOException {
		PDFMergerUtility merger = new PDFMergerUtility();
		for(String src: source) {
			File sourceFile = new File(src);
			merger.addSource(sourceFile);
		}
		merger.setDestinationFileName(Constants.UPLOAD_DIR + "/" + destination + ".pdf");
		merger.mergeDocuments(null);
	}
	
	/*
	 * Convert PDF to image
	 */
	public void convertToImg(String file, String filename) throws IOException {
		File input = new File(file);
		PDDocument doc = Loader.loadPDF(input);
		PDFRenderer renderer = new PDFRenderer(doc);
		//Enter index of page to convert
		int pages = doc.getNumberOfPages();
		for(int i = 0; i< pages; i++) {
			BufferedImage image = renderer.renderImage(i);
			ImageIO.write(image, "JPEG", new File(Constants.UPLOAD_DIR + "/" + filename + " " +  (i+1) + ".jpeg"));
		}
		doc.close();
	}
}
