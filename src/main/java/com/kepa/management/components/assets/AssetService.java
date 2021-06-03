package com.kepa.management.components.assets;

import com.kepa.management.components.assignment.Assignment;
import com.kepa.management.components.assignment.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService {
    private AssetRepository assetRepository;
    private AssetMapper assetMapper;
    private AssignmentRepository assignmentRepository;


    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper, AssignmentRepository assignmentRepository) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
        this.assignmentRepository = assignmentRepository;
    }

    List<AssetDto> findAll() {
        return assetRepository.findAll()
                .stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

    List<AssetDto> findAllByNameOrSerialNumber(String text) {
        return assetRepository.findAllByNameOrSerialNumber(text)
                .stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

    AssetDto save(AssetDto asset) {
        Optional<Asset> assetBySerialNumber = assetRepository.findBySerialNumber(asset.getSerialNumber());
        assetBySerialNumber.ifPresent(u -> {
            throw new DuplicateException();
        });
        return mapAndSaveAsset(asset);
    }
   AssetDto delete(Long assetId){
       System.out.println(assetId);
        Optional<Asset> asset=assetRepository.findById(assetId);
        Asset assetEntity = asset.orElseThrow(AssetNotFoundException::new);
        List<Assignment> assignments= assetEntity.getAssignments();
       if(!assignments.isEmpty()) {
           assignments.forEach(assignment -> {
               assignmentRepository.deleteById(assignment.getId());
           });
       }
        assetRepository.deleteById(assetId);
        return assetMapper.toDto(assetEntity);
    }
    AssetDto update(AssetDto asset) {
        Optional<Asset> assetBySerialNumber = assetRepository.findBySerialNumber(asset.getSerialNumber());
        assetBySerialNumber.ifPresent(u -> {
            if (!u.getId().equals(asset.getId()))
                throw new DuplicateException();
        });
        return mapAndSaveAsset(asset);
    }

    Optional<AssetDto> findById(Long id) {
        return assetRepository.findById(id).map(assetMapper::toDto);
    }

    List<AssetAssignmentDto> getAssetAssignments(Long id) {
        return assetRepository.findById(id)
                .map(Asset::getAssignments)
                .orElseThrow(AssetNotFoundException::new)
                .stream()
                .map(AssetAssignmentMapper::toDto)
                .collect(Collectors.toList());
    }

    private AssetDto mapAndSaveAsset(AssetDto user) {
        Asset assetEntity = assetMapper.toEntity(user);
        Asset savedAsset = assetRepository.save(assetEntity);
        return assetMapper.toDto(savedAsset);
    }

}
