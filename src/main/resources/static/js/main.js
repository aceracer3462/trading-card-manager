// ===== PAGINATION FUNCTIONS =====
function changePageSize(size) {
    console.log('Changing page size to:', size);
    const url = new URL(window.location.href);
    url.searchParams.set('size', size);
    url.searchParams.set('page', '0');
    showLoading();
    window.location.href = url.toString();
}

function goToPage(event) {
    event.preventDefault();
    const pageInput = document.getElementById('goToPageInput');
    if (!pageInput) return;
    
    const pageNum = parseInt(pageInput.value) - 1;
    const totalPagesElement = document.querySelector('[data-total-pages]');
    const totalPages = totalPagesElement ? parseInt(totalPagesElement.dataset.totalPages) : 1;
    
    if (pageNum >= 0 && pageNum < totalPages) {
        const url = new URL(window.location.href);
        url.searchParams.set('page', pageNum);
        showLoading();
        window.location.href = url.toString();
    } else {
        alert(`Please enter a page number between 1 and ${totalPages}`);
    }
}

// ===== SORTING FUNCTIONS =====
function sortTable(column) {
    console.log('Sorting by:', column);
    const url = new URL(window.location.href);
    const currentSort = url.searchParams.get('sortBy');
    const currentDirection = url.searchParams.get('direction') || 'asc';
    
    let newDirection = 'asc';
    if (currentSort === column) {
        newDirection = currentDirection === 'asc' ? 'desc' : 'asc';
    }
    
    url.searchParams.set('sortBy', column);
    url.searchParams.set('direction', newDirection);
    url.searchParams.set('page', '0');
    showLoading();
    window.location.href = url.toString();
}

// ===== FILTER FUNCTIONS =====
function filterByPrice(min, max) {
    const url = new URL(window.location.href);
    url.searchParams.set('minPrice', min);
    url.searchParams.set('maxPrice', max);
    url.searchParams.set('page', '0');
    showLoading();
    window.location.href = url.toString();
}

// ===== UI HELPER FUNCTIONS =====
function updateShowingTo() {
    try {
        const showingToElement = document.getElementById('showingTo');
        if (showingToElement) {
            const fromElement = document.querySelector('small span:first-of-type');
            const totalItemsElement = document.querySelector('small span:last-of-type');
            
            if (fromElement && totalItemsElement) {
                const from = parseInt(fromElement.textContent.replace(/,/g, '')) || 1;
                const pageSizeSelect = document.querySelector('select[onchange*="changePageSize"]');
                const pageSize = pageSizeSelect ? parseInt(pageSizeSelect.value) : 10;
                const totalItems = parseInt(totalItemsElement.textContent.replace(/,/g, '')) || 0;
                
                const toValue = Math.min(from + pageSize - 1, totalItems);
                showingToElement.textContent = toValue.toLocaleString();
            }
        }
    } catch (e) {
        console.log('Could not update showing to value:', e);
    }
}

function enhancePaginationLinks() {
    const paginationLinks = document.querySelectorAll('.pagination a.page-link');
    const currentUrl = new URL(window.location.href);
    
    paginationLinks.forEach(link => {
        const href = link.getAttribute('href');
        if (href && href.startsWith('/?')) {
            try {
                const url = new URL(href, window.location.origin);
                
                // Preserve all current query parameters
                currentUrl.searchParams.forEach((value, key) => {
                    if (key !== 'page' && !url.searchParams.has(key)) {
                        url.searchParams.set(key, value);
                    }
                });
                
                link.setAttribute('href', url.pathname + url.search);
            } catch (e) {
                console.log('Error enhancing link:', e);
            }
        }
    });
}

function showLoading() {
    const container = document.querySelector('.container');
    if (container) {
        container.classList.add('loading');
        
        const overlay = document.createElement('div');
        overlay.className = 'loading-overlay';
        overlay.innerHTML = `
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
        `;
        document.body.appendChild(overlay);
        
        // Remove overlay after navigation (though this won't fire due to page reload)
        setTimeout(() => {
            if (overlay.parentNode) {
                overlay.remove();
            }
        }, 5000);
    }
}

// ===== INITIALIZATION =====
function initializePage() {
    console.log('Initializing Trading Card Manager...');
    
    // Update showing to value
    updateShowingTo();
    
    // Enhance pagination links
    enhancePaginationLinks();
    
    // Add animation classes
    const cards = document.querySelectorAll('.card-gallery .card');
    cards.forEach((card, index) => {
        card.classList.add('fade-in');
        card.style.animationDelay = `${index * 0.05}s`;
    });
    
    // Add row click handlers for table
    const tableRows = document.querySelectorAll('tbody tr');
    tableRows.forEach(row => {
        row.classList.add('cursor-pointer');
        row.addEventListener('click', function(e) {
            // Don't trigger if clicking on links, buttons, or form elements
            if (!e.target.closest('a') && !e.target.closest('button') && 
                !e.target.closest('select') && !e.target.closest('input')) {
                const link = this.querySelector('td a');
                if (link && link.href) {
                    window.location.href = link.href;
                }
            }
        });
    });
    
    // Initialize sortable headers
    const sortableHeaders = document.querySelectorAll('.sortable');
    sortableHeaders.forEach(header => {
        header.addEventListener('click', function() {
            const onclickAttr = this.getAttribute('onclick');
            if (onclickAttr) {
                const match = onclickAttr.match(/sortTable\('([^']+)'\)/);
                if (match && match[1]) {
                    sortTable(match[1]);
                }
            }
        });
    });
    
    // Initialize quick filter buttons
    const quickFilterButtons = document.querySelectorAll('.btn-group-sm .btn[href*="minPrice"]');
    quickFilterButtons.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const href = this.getAttribute('href');
            if (href) {
                showLoading();
                window.location.href = href;
            }
        });
    });
    
    console.log('Page initialization complete');
}

// ===== EVENT LISTENERS =====
document.addEventListener('DOMContentLoaded', function() {
    // Set up page size selector
    const pageSizeSelect = document.querySelector('select[onchange*="changePageSize"]');
    if (pageSizeSelect) {
        pageSizeSelect.addEventListener('change', function() {
            changePageSize(this.value);
        });
        // Remove the inline onchange
        pageSizeSelect.removeAttribute('onchange');
    }
    
    // Set up go to page form
    const goToPageForm = document.querySelector('form[onsubmit*="goToPage"]');
    if (goToPageForm) {
        goToPageForm.addEventListener('submit', goToPage);
        // Remove the inline onsubmit
        goToPageForm.removeAttribute('onsubmit');
    }
    
    // Initialize the page
    initializePage();
});

// ===== TOAST NOTIFICATIONS =====
function showToast(message, type = 'success') {
    // Create toast container if it doesn't exist
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        container.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 10000;
        `;
        document.body.appendChild(container);
    }
    
    // Create toast
    const toast = document.createElement('div');
    toast.className = `alert alert-${type} alert-dismissible fade show`;
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    container.appendChild(toast);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            toast.remove();
        }, 300);
    }, 5000);
}

// Add CSS for slide-in animation
const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    #toast-container .alert {
        animation: slideInRight 0.3s ease-out;
        margin-bottom: 10px;
        min-width: 300px;
    }
`;
document.head.appendChild(style);