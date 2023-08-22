package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.domain.PeriodicData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicDataRepository extends JpaRepository<PeriodicData, Long> {
}

