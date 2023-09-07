package com.meysamzamani.ProductAPI.presentation.controller;

import com.meysamzamani.ProductAPI.application.CampaignService;
import com.meysamzamani.ProductAPI.domain.Campaign;
import com.meysamzamani.ProductAPI.domain.Product;
import com.meysamzamani.ProductAPI.presentation.dto.CampaignRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Campaign registerNewCampaign(@Valid @RequestBody CampaignRequestDTO campaign) {
        return campaignService.createCampaign(campaign);
    }




}
