package product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripmaven.repository.ProductBoardEntity;

import lombok.RequiredArgsConstructor;

	// 상품게시판 등록 및 관리 
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	
	//검색
	
	//게시글 전체 조회
	@Transactional
	public List<ProductDTO> listAll() {
		List<ProductBoardEntity> postEntityList = productRepository.findAll();
		return postEntityList.stream().map(user->ProductDTO.toDto(user)).collect(Collectors.toList());
	} //listAll() : 게시글 전체 조회
	
	// 게시글 제목으로 검색
	@Transactional(readOnly = true)
	public List<ProductDTO> searchByTitle(String findTitle) {
		List<ProductBoardEntity> products = productRepository.findByTitleContaining(findTitle);
		List<ProductDTO> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductDTO.toDto(product));
		} 
		return productsDto;
	}	
	// 게시물 내용으로 검색
	@Transactional(readOnly = true)
	public List<ProductDTO> searchByContent(String findContent) {
		List<ProductBoardEntity> products=productRepository.findByContentContaining(findContent);
		List<ProductDTO> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductDTO.toDto(product));
		} 
		return productsDto;
	}
	// 게시글 도시로 찾기 
	@Transactional(readOnly = true)
	public List<ProductDTO> searchByCity(String findCity) {
		List<ProductBoardEntity> products=productRepository.findByCityContaining(findCity);
		List<ProductDTO> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductDTO.toDto(product));
		} 
		return productsDto;
	}
	
	// 게시글 회원고유번호(아이디)로 찾기 
		@Transactional(readOnly = true)
		public ProductDTO searchByMemberId(Long findMember) {
			return ProductDTO.toDto(productRepository.findByMember(findMember).get());
		}  
		// 제목 + 내용
	@Transactional(readOnly = true)
	public List<ProductDTO> searchByTitleAndContent(String keyword) {
		List<ProductBoardEntity> products=productRepository.findByTitleAndContent(keyword);
		List<ProductDTO> productsDto = new Vector<>();
		for(ProductBoardEntity product : products) {
			productsDto.add(ProductDTO.toDto(product));
		} 
		return productsDto;
		
	}

/////////////////////

	//게시글 수정
	@Transactional
	public ProductDTO update(Long id,ProductDTO dto) {
		ProductBoardEntity productBoardEntity=productRepository.findById(id).get();
		productBoardEntity.setTitle(dto.getTitle());
		productBoardEntity.setContent(dto.getContent());
		productBoardEntity.setCity(dto.getCity());
		productBoardEntity.setIsupdate("1"); //수정여부
		productBoardEntity.setUpdatedAt(LocalDateTime.now()); //수정시간
		return ProductDTO.toDto(productRepository.save(productBoardEntity));
	} 

////////////////////////////
	
	//게시글 삭제 여부
	@Transactional
	public ProductDTO delete(Long id,ProductDTO dto) {
		ProductBoardEntity productBoardEntity=productRepository.findById(id).get();
		productBoardEntity.setIsupdate("1"); //삭제여부
		productBoardEntity.setDeletedAt(LocalDateTime.now()); //삭제시간
		return ProductDTO.toDto(productRepository.save(productBoardEntity));
	}
	
	/////////////////////
	
	
	
}
