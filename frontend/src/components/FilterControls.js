import React from 'react';

function FilterControls({
                            kategorieData,
                            filterData,
                            selectedKategorie,
                            selectedFilter,
                            onKategorieChange,
                            onFilterChange,
                            onAddFilter
                        }) {
    return (
        <>
            {/* Dropdown-Menü für Merkmalskategorie */}
            <div className="dropdown-container">
                <label htmlFor="kategorieDropdown" className="dropdown-label">Merkmalskategorie:</label>
                <select
                    id="kategorieDropdown"
                    value={selectedKategorie}
                    onChange={onKategorieChange}
                    className="dropdown-select"
                >
                    <option value="">-</option>
                    {kategorieData.map(kategorie => (
                        <option key={kategorie.id} value={kategorie.id}>
                            {kategorie.bezeichner}
                        </option>
                    ))}
                </select>
            </div>

            {/* Dropdown-Menü für Filter */}
            <div className="filter-dropdown-container">
                <label htmlFor="filterDropdown" className="dropdown-label">Filter:</label>
                <select
                    id="filterDropdown"
                    value={selectedFilter}
                    onChange={onFilterChange}
                    className="dropdown-select"
                >
                    <option value="">-</option>
                    {filterData.map(filter => (
                        <option key={filter.id} value={filter.id}>
                            {filter.name}
                        </option>
                    ))}
                </select>
                <button
                    className="add-filter-button"
                    onClick={onAddFilter}
                    aria-label="Neuen Filter anlegen"
                    title="Neuen Filter anlegen"
                >
                    +
                </button>
            </div>
        </>
    );
}

export default FilterControls;