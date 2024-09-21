package com.ssb.repository;

import com.ssb.model.EarthquakeEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EarthquakeRepository extends JpaRepository<EarthquakeEvent, String> {

}
