package com.tradingcards.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {
    
    public Pageable createPageable(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }
    
    public int[] getPageNumbers(int currentPage, int totalPages) {
        if (totalPages <= 0) {
            return new int[0];
        }
        
        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);
        
        // Adjust if at start or end
        if (currentPage < 3) {
            endPage = Math.min(4, totalPages - 1);
        }
        if (currentPage > totalPages - 4) {
            startPage = Math.max(totalPages - 5, 0);
        }
        
        int[] pages = new int[endPage - startPage + 1];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = startPage + i;
        }
        return pages;
    }
    
    public boolean hasPrevious(int currentPage) {
        return currentPage > 0;
    }
    
    public boolean hasNext(int currentPage, int totalPages) {
        return currentPage < totalPages - 1;
    }
}