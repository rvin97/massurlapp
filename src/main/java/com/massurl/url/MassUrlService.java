package com.massurl.url;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MassUrlService {

	@Autowired
	private MassUrlRepository massUrlRepository;

	private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	@Value("${massurl.is_prod}")
	private boolean isProd;
	@Value("${massurl.home.url.dev}")
	private String homeUrlDev;
	@Value("${massurl.home.url.prod}")
	private String homeUrlProd;

	public String shortenUrl(MassUrl massUrl) {
		massUrl.setViewCount(0);
		massUrlRepository.save(massUrl);

		return getHomeUrl() + base10ToBase62(massUrl.getMassUrlId());
	}

	public List<MassUrl> getUrls() {
		List<MassUrl> massUrls = new ArrayList<>();
		massUrlRepository.findAll().forEach(massUrls::add);

		return massUrls;
	}

	public MassUrl getUrlDetails(long massUrlID) throws Exception {
		Optional<MassUrl> massUrlOptional = massUrlRepository.findById(massUrlID);
		if (!massUrlOptional.isPresent()) {
			throw new UrlNotFoundException();
		}

		return massUrlOptional.get();
	}

	public String getOriginalUrl(String massUrlID) throws Exception {
		MassUrl massUrl = getUrlDetails(base62ToBase10(massUrlID));
		increaseViewCount(massUrl);

		return massUrl.getOriginalUrl();
	}

	public void increaseViewCount(MassUrl massUrl) {
		long viewCount = massUrl.getViewCount();
		massUrl.setViewCount(++viewCount);

		massUrlRepository.save(massUrl);
	}

	private static long base62ToBase10(String base62Id) {
		long base10Id = 0;
		for (int i = 0; i < base62Id.length(); i++) {
			base10Id = base10Id * 62 + BASE62.indexOf("" + base62Id.charAt(i));
		}
		return base10Id;
	}

	private static String base10ToBase62(long base10Id) {
		StringBuilder sb = new StringBuilder();
		while (base10Id != 0) {
			sb.append(BASE62.charAt((int) (base10Id % 62)));
			base10Id /= 62;
		}
		while (sb.length() < 8) {
			sb.append(0);
		}
		return sb.reverse().toString();
	}

	private String getHomeUrl() {
		return isProd ? homeUrlProd : homeUrlDev;
	}

}
