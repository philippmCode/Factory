import React, { useState } from 'react';

function AddFilterModal({ show, kategorieData, onSave, onClose }) {
    // Zustand für den Namen des neuen Filters (jetzt intern im Modal)
    const [newFilterName, setNewFilterName] = useState('');
    // Zustand für die ausgewählten Merkmalskategorien des neuen Filters (jetzt intern im Modal)
    const [selectedCategoriesForNewFilter, setSelectedCategoriesForNewFilter] = useState([]);

    // Wenn 'show' false ist, wird nichts gerendert.
    if (!show) {
        return null;
    }

    // Handler für Änderungen im Input-Feld des neuen Filternamens
    const handleNewFilterNameChange = (event) => {
        setNewFilterName(event.target.value);
    };

    // Handler für die Auswahl/Abwahl von Merkmalskategorien im Filter-Erstellungs-Modal
    const handleCategorySelection = (categoryId) => {
        setSelectedCategoriesForNewFilter(prevSelected => {
            if (prevSelected.includes(categoryId)) {
                // Kategorie war bereits ausgewählt, jetzt abwählen
                return prevSelected.filter(id => id !== categoryId);
            } else {
                // Kategorie war nicht ausgewählt, jetzt auswählen
                return [...prevSelected, categoryId];
            }
        });
    };

    // Handler zum Speichern des neuen Filters
    const handleSaveNewFilter = () => {
        if (!newFilterName.trim()) {
            alert('Bitte geben Sie einen Namen für den Filter ein.');
            return;
        }
        if (selectedCategoriesForNewFilter.length === 0) {
            alert('Bitte wählen Sie mindestens eine Merkmalskategorie aus.');
            return;
        }

        const newFilter = {
            name: newFilterName.trim(),
            merkmalskategorieIds: selectedCategoriesForNewFilter
        };

        fetch('http://localhost:8081/filter', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newFilter),
        })
            .then(res => {
                if (!res.ok) {
                    // Versuche, den Fehlertext vom Server zu lesen
                    return res.json().then(err => { throw new Error(err.message || `HTTP error! status: ${res.status}`); });
                }
                return res.json();
            })
            .then(data => {
                alert('Filter erfolgreich erstellt!');
                // Formular zurücksetzen (intern im Modal)
                setNewFilterName('');
                setSelectedCategoriesForNewFilter([]);
                onClose(); // Modal schließen über den von App.js übergebenen Callback
                onSave(); // Filterliste in App.js aktualisieren über den Callback
            })
            .catch(error => console.error("Fehler beim Erstellen des Filters:", error.message));
    };

    // Handler zum Abbrechen der Filtererstellung
    const handleCancelAddFilter = () => {
        // Formular zurücksetzen (intern im Modal)
        setNewFilterName('');
        setSelectedCategoriesForNewFilter([]);
        onClose(); // Modal schließen über den von App.js übergebenen Callback
    };

    return (
        <div className="modal-overlay" onClick={handleCancelAddFilter}>
            <div className="modal-content add-filter-modal" onClick={(e) => e.stopPropagation()}>
                <h2 className="dialog-title">Neuen Filter erstellen</h2>

                <div className="form-group">
                    <label htmlFor="newFilterName">Filtername:</label>
                    <input
                        type="text"
                        id="newFilterName"
                        value={newFilterName}
                        onChange={handleNewFilterNameChange}
                        placeholder="Geben Sie den Filternamen ein"
                        className="text-input"
                    />
                </div>

                <div className="form-group">
                    <label>Merkmalskategorien auswählen:</label>
                    <div className="category-selection-container">
                        {kategorieData.map(kategorie => (
                            <div key={kategorie.id} className="category-checkbox">
                                <input
                                    type="checkbox"
                                    id={`category-${kategorie.id}`}
                                    checked={selectedCategoriesForNewFilter.includes(kategorie.id)}
                                    onChange={() => handleCategorySelection(kategorie.id)}
                                />
                                <label htmlFor={`category-${kategorie.id}`}>{kategorie.bezeichner}</label>
                            </div>
                        ))}
                    </div>
                </div>

                <div className="modal-actions">
                    <button className="button-primary" onClick={handleSaveNewFilter}>Speichern</button>
                    <button className="button-secondary" onClick={handleCancelAddFilter}>Abbrechen</button>
                </div>

                <button className="dialog-close-button" onClick={handleCancelAddFilter} aria-label="Schließen">&times;</button>
            </div>
        </div>
    );
}

export default AddFilterModal;