package upkit.bp.springmvc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DogService {

    @Transactional
    public Dog update(Dog dog){

        // some database options

        // 模拟狗狗新名字与其他狗狗的名字冲突
        isTrue(false, "狗狗名字已经被使用了...");

        // update database dog info

        return dog;
    }

    private static void isTrue(boolean expression, String error){
        if(!expression) {
            throw new BusinessException(error);
        }
    }
}
