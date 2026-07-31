package com.mytri.genshin_calculator;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

// this is a web controller that receives requests from the frontend and returns data
@RestController

// All requests here start with '/pity'
@RequestMapping("/pity")

public class PityController {

    // run this method when /pity/calculate is requested
    @GetMapping("/calculate")
    public PityResponse calculate(

            // grab these three params from the URL
            @RequestParam String banner,
            @RequestParam int currentPity,
            @RequestParam (required = false, defaultValue = "false") boolean isGuaranteed,
            @RequestParam (required = false, defaultValue = "false") boolean isWeaponGuaranteed,
            @RequestParam (required = false, defaultValue = "0") int fatePoints) {


        String recommendation = "";
        int[] simulations = simulate100000(currentPity, isGuaranteed, banner, isWeaponGuaranteed,  fatePoints);
        double[] probabilities = percentages(simulations);

        // text to display for certain pity ranges
        if (currentPity >= 90) {
            if (banner.equals("character") || banner.equals("chronicled")) {
                if(isGuaranteed) {
                    recommendation = "Jackpot";
                }
                else {
                    recommendation = "Don't get mad if its Qiqi!";
                }
            }
            else {
                recommendation = "Jackpot!";
            }
        } else if (currentPity >= 74) {
            if(banner.equals("standard")) {
                recommendation = "About time your standard arrived!";
            }
            else if(isGuaranteed) {
                recommendation = "About time the gold appeared!!";
            }
            else {
                recommendation = "Don't get mad if its Qiqi!";
            }
        } else if (currentPity >= 50) {
            recommendation = "Keep building pity! Persistence is key!";
        } else {
            recommendation = "Don't bother if you don't have enough primos!";
        }
        return new PityResponse(recommendation, probabilities);
    }

    private double getProbability(int currentPity, String banner) {
        boolean isWeapon = "weapon".equals(banner);
        int softPityFloor = isWeapon ? 62 : 74;
        int hardPityCap = isWeapon ? 80 : 90;

        if (currentPity >= hardPityCap) {
            return 1.0;
        } else if (currentPity > softPityFloor) {
            double baseRate = 0.006;
            double rampFactor = isWeapon ? 0.07 : 0.06; // Weapons scale up faster
            return baseRate + (rampFactor * (currentPity - softPityFloor));
        } else {
            return 0.006;
        }
    }

    private int simulateOnce(int currentPity, boolean isGuaranteed, String banner, boolean isWeaponGuaranteed, int fatePoints) {
        boolean gotFeatured = false;
        int totalPulls = 0;
        int fatePoint = fatePoints;
        boolean featuredWeapon = isWeaponGuaranteed;

        // ThreadLocalRandom is optimized for high-performance simulations
        ThreadLocalRandom random = ThreadLocalRandom.current();

        while (!gotFeatured) {
            totalPulls++;
            currentPity++;

            // 1. Did we roll a 5-star?
            if (random.nextDouble() <= getProbability(currentPity, banner)) {

                // 2. Standard banner has no 50/50 mechanic, finish instantly
                if ("standard".equals(banner)) {
                    gotFeatured = true;
                }
                else if ("weapon".equals(banner)) {
                    if (fatePoint >= 1) {
                        gotFeatured = true;
                    }
                    else if (random.nextDouble() <= 0.25 && !featuredWeapon) {
                        featuredWeapon = true;
                        currentPity = 0;
                        fatePoint++;
                    }
                    else {
                        if (random.nextDouble() <= 0.5) {
                            gotFeatured = true;
                        } else {
                            fatePoint++;
                            currentPity = 0;
                        }
                    }
                }
                // 3. Limited banners check guarantee status
                else if (isGuaranteed) {
                    gotFeatured = true; // Win condition met!
                } else {
                    // 50/50 Coin Flip
                    if (random.nextDouble() < 0.50) {
                        gotFeatured = true; // Won the 50/50! Loop ends.
                    } else {
                        isGuaranteed = true; // Lost 50/50. Loop continues.
                        currentPity = 0;     // Pity resets to 0 because we hit a standard 5-star
                    }
                }
            }
        }
        return totalPulls;
    }

    private int[] simulate100000(int currentPity, boolean isGuaranteed, String banner, boolean isWeaponGuaranteed, int fatePoints) {
        int[] results = new int[100000];
        for (int i = 0; i < 100000; i++) {
            results[i] = simulateOnce(currentPity, isGuaranteed, banner, isWeaponGuaranteed, fatePoints);
        }
        return results;
    }

    private double[] percentages(int[] simulations) {
        // Index 0 means 0 pulls (0% chance). Indices 1 to 180 store actual counts.
        double[] cumulativePercentages = new double[181];
        int[] pullBuckets = new int[181];

        // Count how many times each specific pull count occurred
        for (int pullCount : simulations) {
            if (pullCount <= 180) {
                pullBuckets[pullCount]++;
            }
        }

        // Accumulate the numbers sequentially (O(N) performance)
        int runningSum = 0;
        for (int i = 1; i <= 180; i++) {
            runningSum += pullBuckets[i];
            cumulativePercentages[i] = ((double) runningSum / 100000.0) * 100.0;
        }

        return cumulativePercentages;
    }

}
