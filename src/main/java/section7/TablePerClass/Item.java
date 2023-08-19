package section7.TablePerClass;

import javax.persistence.*;

@Entity
/**
 * 쓰지 않는게 좋다.
 * 데이터베이스 설계자와 ORM 설계자 둘 다 싫어함.
 * 뭔가 묶이고, 연결되는 게 없다.
 * 새로운게 추가될 때 마다 난리가 난다.
 * <장점>
 *     1. 서브 타입을 명확하게 구분해서 처리할 때 효과적
 *     2. NOT NULL 제약조건을 사용할 수 있다.
 * </장점>
 * <단점>
 *     1. 여러 자식 테이블을 조회할 때 성능이 느리다.(union)
 *     2. 자식 테이블을 통합해서 처리하기 힘들다.
 * </단점>
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 아이템 클래스의 속성을 각 클래스의 컬럼으로 내린 형태롬 만들어지며, 아이템 클래스는 생성되지 않는다.
// 추상 클래스로 만들어야 아이템 테이블이 생성되지 않는다.
// 추상 클래스가 아니면, 아이템 자체 클래스도 사용한다고 판단해서 아이템 테이블도 생성한다.
public abstract class Item {

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
