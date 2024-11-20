package ma.nabil.Citronix.services;

import ma.nabil.Citronix.dtos.requests.FarmRequest;
import ma.nabil.Citronix.dtos.responses.FarmResponse;


public interface FarmService {
    FarmResponse create(FarmRequest request);
}