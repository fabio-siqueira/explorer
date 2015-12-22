package br.com.company.explorer.service;

import br.com.company.explorer.domain.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fábio Siqueira on 12/18/15.
 */
@Repository
public interface ProbeRepository extends JpaRepository<Probe, Long> {

}

