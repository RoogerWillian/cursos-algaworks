package com.algaworks.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.createDirectories;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {

	private Path local;
	private Path localTemporario;

	public FotoStorageLocal() {
		this(getDefault().getPath("HOME", "brewerfotos"));
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoArquivo = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			try {
				novoArquivo = renomearArquivo(arquivo.getOriginalFilename());
				arquivo.transferTo(
						new File(this.localTemporario.toAbsolutePath() + getDefault().getSeparator() + novoArquivo));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando foto temporaria", e);
			}
		}

		return novoArquivo;
	}

	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro salvando foto definitiva");
		}

		try {
			Thumbnails.of(local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerar thumbnail", e);
		}
	}

	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo foto temporaria", e);
		}
	}
	
	@Override
	public byte[] recuperarFoto(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo foto", e);
		}
	}
	
	private void criarPastas() {
		try {
			createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			createDirectories(this.localTemporario);

		} catch (IOException e) {
			throw new RuntimeException("Erro criando pastas para salvar foto", e);
		}
	}

	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID() + "_" + nomeOriginal;
		return novoNome;
	}
}
