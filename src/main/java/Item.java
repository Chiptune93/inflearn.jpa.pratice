import javax.persistence.*;

@Entity
/**
 * 이 방법이 약간 정석과 같은 느낌이다.
 * <장점>
 *     1. 정규화 되어있다.
 *     2. 외래 키 참조 무결성 제약조건을 활용할 수 있다. -> ITEM_ID를 사용하여 활용가능하다. 설계가 깔끔해진다.
 *     3. 저장공간의 효율성.
 * </장점>
 * <단점>
 *     1. 조회 시, 조인을 많이 사용하여 성능 저하.
 *     2. 조회 쿼리가 복잡하다.
 *     3. 데이터 저장 시 insert 쿼리가 2번 나간다.
 * </단점>
 */
@Inheritance(strategy = InheritanceType.JOINED) // 아이템의 하위 테이블(FK로 연결되어있는 구조)로 extends 한 클래스들이 들어오게 된다.
/**
 *  DTYPE 생성, 엔티티명으로 기본적으로 들어가게 된다.
 *  카테고리 타입 같은 느낌. 이게 없으면 데이터가 어떤 클래스 때문에 들어온 것인지 모르기 때문에 있는게 낫다.
 *  자식 클래스에서 @DiscriminatorValue 를 통해 들어갈 값을 지정할 수 있다.
 *  필수는 아니지만 있으면 운영에 편하다 (싱글 테이블 전략에서는 필수, 슈퍼타입/서브타입 논리 모델에서는 필수는 아니다.)
 */
@DiscriminatorColumn
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
