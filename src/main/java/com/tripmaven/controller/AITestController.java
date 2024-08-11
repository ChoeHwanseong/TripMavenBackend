package com.tripmaven.controller;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.tripmaven.service.VisionDto;



@RestController
public class AITestController {
	@Autowired
	private RestTemplate restTemplate;
	
	
	//수업때 한거 예시로 가져옴
	@PostMapping("/vision")
	@CrossOrigin
	public Map objectDetect(@RequestBody Map params) {
		//401:RestTemplete으로 HTTP 요청시 인증 오류처리는 아래 에러 핸들러 추가
		/*
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				HttpStatus status= (HttpStatus)response.getStatusCode();
				return status.series() == HttpStatus.Series.SERVER_ERROR;
			}			
		}); */
		//1.RestTemplate으로 요청보낼때 요청헤더 설정을 위한 HttpHeaders 객체 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer ya29.a0AXooCgsX4CVe733PCfYk14dIQBtgWGH1kgH2sMMieF6Bb3whA1NUKWixA39E-rPG1cwo3pl4DtjH6xm4htEh911ZsRJEizrvdfbFjU-RrTD_EuwMvDSISIrWagoWtLKnc6ZylUx_SI7Y7iasdaZ1t5J0YyI_Svaw2QD4xW1P06IaCgYKASkSARMSFQHGX2Mi-6Mm_96au9slaLDLyIiVnQ0178");
		headers.add("x-goog-user-project", "vision-object-detect20240627");
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		//2. 구글의 요청본문(JSON형태)과 동일한 구조의 Dto로 요청바디 설정
		VisionDto body = new VisionDto();
		VisionDto.Image image = new VisionDto.Image();
		image.setContent(params.get("base64").toString());
		VisionDto.Feature feature = new VisionDto.Feature();
		feature.setMaxResults((long)30);
		feature.setType(params.get("model").toString());
		
		VisionDto.Request request = new VisionDto.Request();
		request.setFeatures(Arrays.asList(feature));
		request.setImage(image);
		
		body.setRequests(Arrays.asList(request));
		
		//3. 요청헤더/요청바디(페이로드) 정보 등을 담은 HttpEntity 객체 생성
		//  Dto 혹은 Map에는 구글로 요청시 구글 REST API 서버에 보낼 데이터를 담는다
		//  key=value 일때는 반드시 MultiValueMap 사용
		//  데이터가 JSON일때는 MultiValueMap 또는 Map 사용
		HttpEntity entity = new HttpEntity(body, headers);
		
		//4. RestTemplate으로 요청 보내기
		//  String url="한글이 포함된 요청 URI"
		//  요청 URL에 한글 포함시는 UriComponents로 객체 생성 후 사용시는 uri.toString()해서 사용한다
		//  UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
		String url = "https://vision.googleapis.com/v1/images:annotate";
		//※외부 OPEN API(구글 REST API)서버로부터 받는 데이터 타입이 {}인 경우 Map 또는 Dto
		ResponseEntity<Map> response = restTemplate.exchange(
				url, //요청하려는 url
				HttpMethod.POST, //요청 메소드
				entity, //HttpEntity(요청바디와 요청헤더 설정된 엔터티)
				Map.class); //응답 데이터 타입의 class
		
		System.out.println("응답코드:"+response.getStatusCode().value());
		System.out.println("응답코드:"+response.getHeaders());
		System.out.println("응답코드:"+response.getBody());
		return response.getBody();
	}
	
	//우리가 써야할 api
	@PostMapping("path")
	public String postMethodName(@RequestBody String entity) {
		
		return entity;
	}
	
}
