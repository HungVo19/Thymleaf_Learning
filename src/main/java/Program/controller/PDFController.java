package Program.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Program.pdfbox.PDFBox;
import Program.utils.UploadFile;

@Controller
@RequestMapping("/pdfbox")
public class PDFController {
	@Autowired
	private PDFBox pdf;
	
	@GetMapping
	public String toPage( ) {
		return "pdfbox";
	}
	
	@PostMapping("/create-PDF-with-text")
	public String createPDFwithText(@RequestParam(value = "fileName", required =false, defaultValue="defaultName") String fileName,
									@RequestParam(value = "text", required = false, defaultValue = "") String text,
									Model model) throws IOException {
		String path = UploadFile.createPath(fileName);
		this.pdf.createPDFWithText(text, path);
		model.addAttribute("createMsg","create file successfully");
		model.addAttribute("activeAccordion","accordionOne");
		return "pdfbox";
	}
	
	@PostMapping("/read-PDF")
	public String readFile(@RequestParam("file") MultipartFile file, Model model ) throws IOException {
		String pdf = UploadFile.upload(file);
		String content = this.pdf.readPDF(pdf);
		model.addAttribute("content",content);
		model.addAttribute("activeAccordion","accordionTwo");
		return "pdfbox";
	}
	
	@PostMapping("/insert-image")
	public String insertImage(@RequestParam(value = "img") MultipartFile img,
							  @RequestParam(value = "fileToInsert") MultipartFile file,
							  Model model) throws IOException {
		String pdf = UploadFile.upload(file);
		String image = UploadFile.upload(img);
		this.pdf.insertImg(pdf, image);
		model.addAttribute("insertMsg","insert successfully");
		model.addAttribute("activeAccordion","accordionThree");
		return "pdfbox";
	}
	
	@PostMapping("/encrypt-file")
	public String encryptFile(@RequestParam(value = "fileToEncrypt") MultipartFile file,
							  @RequestParam(value = "pass", required = false, defaultValue = "admin") String pass,
							  Model model) throws IOException {
		String filePath = UploadFile.upload(file);
		this.pdf.encryptFile(filePath, pass);
		model.addAttribute("encryptMsg","encrpt successfully");
		model.addAttribute("activeAccordion","accordionFour");
		return "pdfbox";
	}
	
	@PostMapping("/add-js")
	public String addJavaScript(@RequestParam(value = "fileToAddJS") MultipartFile fileToAddJS, Model model) throws IOException {
		String filePath = UploadFile.upload(fileToAddJS);
		this.pdf.addJs(filePath);
		model.addAttribute("addJSMsg","add successfully");
		model.addAttribute("activeAccordion","accordionFive");
		return "pdfbox";
	}
	
	@PostMapping("/split")
	public String splitPages(@RequestParam(value = "fileToSplit") MultipartFile fileToSplit, 
							 Model model) throws IOException {
		String name = fileToSplit.getOriginalFilename().substring(0,fileToSplit.getOriginalFilename().length()-4);
		String filePath = UploadFile.upload(fileToSplit);
		this.pdf.splitPages(filePath, name);
		model.addAttribute("splitMsg","split successfully");
		model.addAttribute("activeAccordion","accordionSix");
		return "pdfbox";
	}
	
	@PostMapping("/merge")
	public String mergeFiles(@RequestParam(value = "filesToMerges") MultipartFile[] sourceFiles,
							 Model model) throws IOException {
		List<String> sourcePath = UploadFile.uploadMultiFiles(sourceFiles);
		String mergedName = "";
		for(MultipartFile file: sourceFiles) {
			mergedName += file.getOriginalFilename().substring(0,file.getOriginalFilename().length() - 4) + " ";
		}
		this.pdf.mergeFiles(sourcePath, mergedName);
		model.addAttribute("mergeMsg","merge successfully");
		model.addAttribute("activeAccordion","accordionSeven");
		return "pdfbox";
	}
	
	@PostMapping("/convertImg")
	public String convertToImg(@RequestParam(value = "fileToImage") MultipartFile fileToImage, 
								Model model) throws IOException {
		String uploadPath = UploadFile.upload(fileToImage);
		String filename = fileToImage.getOriginalFilename().substring(0, fileToImage.getOriginalFilename().length() - 4);
		this.pdf.convertToImg(uploadPath, filename);
		model.addAttribute("cvtMsg","convert successfully");
		model.addAttribute("activeAccordion","accordion8");
		return "pdfbox";
	}
}