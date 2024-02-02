package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private  final EntityManager em;

    public List<Board> findAll(int page){
        final int COUNT = 3;
        int value = page*3;
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?", Board.class);
        query.setParameter(1, value);
        query.setParameter(2, COUNT);

        List<Board> boardList = query.getResultList();

        return boardList;
    }
    public int count(){
        Query query = em.createNativeQuery("select count(*) from board_tb");

        Long count = (Long) query.getSingleResult();

        return count.intValue();
    }

    public BoardResponse.DetailDTO findById(int id) {
        //Entity가 아닌 것은 JPA가 파싱을 안 해준다.
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?");
        query.setParameter(1, id);

        JpaResultMapper rm = new JpaResultMapper();
        BoardResponse.DetailDTO responseDTO = rm.uniqueResult(query, BoardResponse.DetailDTO.class);
        return responseDTO;
    }
}