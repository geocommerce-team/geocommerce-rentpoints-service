package ru.geocommerce.rentpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geocommerce/api/rent-points")
public class RentPointsController {

    private final RentPointsService rentPointsService;

    public RentPointsController(RentPointsService rentPointsService) {
        this.rentPointsService = rentPointsService;
    }

    @GetMapping
    public ResponseEntity<List<Rash>> getRentPoints(
            @RequestParam double left,
            @RequestParam double right,
            @RequestParam double top,
            @RequestParam double bottom) {
        List<Rash> points = rentPointsService.getRentPoints(left, right, top, bottom);
        return ResponseEntity.ok(points);
    }
}