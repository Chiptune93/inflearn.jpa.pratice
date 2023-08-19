package section7.SingleTable;

import javax.persistence.*;

@Entity
/**
 * <장점>
 *     1. 조인이 필요 없어 조회 성능이 빠르다.
 *     2. 조회 쿼리가 단순하다.
 * </장점>
 * <단점>
 *     1. 자식 엔티티가 매핑한 컬럼은 모두 NULL 허용을 해주어야 한다. 한 클래스 데이터인데 나머지 컬럼은 널 허용해줘야 데이터가 들어감.
 *     2. 단일 테이블에 저장한다는 것은 커질 수 있는 가능성이 있어 조회 성능이 느릴 수도 있다.
 * </단점>
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 하위 클래스들이 전부 이 어노테이션이 정의된 테이블 하나로 모이게 된다.
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
