import React, { useEffect, useState } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from 'recharts';
import '../assets/css/pages.css'; // Import der externen CSS-Datei

// Hauptkomponente der Anwendung
function App() {
    // Zustand für die Diagrammdaten (Top 10 Störungen)
    const [data, setData] = useState([]);
    // Zustand für die Merkmalskategoriedaten
    const [kategorieData, setKategorieData] = useState([]);
    // Zustand für die ausgewählte Merkmalskategorie aus dem Dropdown
    const [selectedKategorie, setSelectedKategorie] = useState('');
    // Zustand für Filterdaten
    const [filterData, setFilterData] = useState([]);
    // Zustand für den ausgewählten Filter aus dem Dropdown
    const [selectedFilter, setSelectedFilter] = useState('');

    // Zustand zum Steuern der Sichtbarkeit des Dialogfensters/Modals
    const [showDialog, setShowDialog] = useState(false);
    // Zustand für den Inhalt des Dialogfensters (Daten des angeklickten Balkens)
    const [dialogContent, setDialogContent] = useState(null);

    const [showAddFilterModal, setShowAddFilterModal] = useState(false);
    // NEU: Zustand für den Namen des neuen Filters
    const [newFilterName, setNewFilterName] = useState('');
    // NEU: Zustand für die ausgewählten Merkmalskategorien des neuen Filters
    const [selectedCategoriesForNewFilter, setSelectedCategoriesForNewFilter] = useState([]);

    const fetchFilterData = () => {
        fetch('http://localhost:8081/filter')
            .then(res => {
                if (!res.ok) {
                    throw new Error(`HTTP error! status: ${res.status}`);
                }
                return res.json();
            })
            .then(data => {
                setFilterData(data);
            })
            .catch(error => console.error("Fehler beim Abrufen der Filterdaten:", error));
    };

    // Effekt zum Abrufen der Störungsdaten basierend auf dem ausgewählten Filter
    useEffect(() => {
        let url = 'http://localhost:8081/stoerung/sum';

        if (selectedFilter) {
            url += `?filterId=${selectedFilter}`;
        }

        fetch(url)
            .then(res => {
                if (!res.ok) {
                    throw new Error(`HTTP error! status: ${res.status}`);
                }
                return res.json();
            })
            .then(data => {
                // Daten nach 'summe' sortieren und die Top 10 auswählen
                const top10 = data
                    .sort((a, b) => b.summe - a.summe)
                    .slice(0, 10);
                setData(top10);
            })
            .catch(error => console.error("Fehler beim Abrufen der Störungsdaten:", error));
    }, [selectedFilter]); // Abhängigkeit: aktualisiere, wenn selectedFilter sich ändert

    // Effekt zum Abrufen der Merkmalskategoriedaten
    useEffect(() => {
        fetch('http://localhost:8081/merkmalskategorie')
            .then(res => {
                if (!res.ok) {
                    throw new Error(`HTTP error! status: ${res.status}`);
                }
                return res.json();
            })
            .then(data => {
                setKategorieData(data);
            })
            .catch(error => console.error("Fehler beim Abrufen der Merkmalskategorien:", error));
    }, []); // Leeres Array bedeutet, dass dieser Effekt nur einmal beim Mounten ausgeführt wird

    // Effekt zum Abrufen der Filterdaten
    useEffect(() => {
        fetch('http://localhost:8081/filter')
            .then(res => {
                if (!res.ok) {
                    throw new Error(`HTTP error! status: ${res.status}`);
                }
                return res.json();
            })
            .then(data => {
                setFilterData(data);
            })
            .catch(error => console.error("Fehler beim Abrufen der Filterdaten:", error));
    }, []); // Leeres Array bedeutet, dass dieser Effekt nur einmal beim Mounten ausgeführt wird

    // Handler für Änderungen im Merkmalskategorie-Dropdown
    const handleKategorieChange = (event) => {
        setSelectedKategorie(event.target.value);
    };

    const handleFilterChange = (event) => {
        setSelectedFilter(event.target.value);
    };

    const handleAddFilter = () => {
        setShowAddFilterModal(true); // Öffne das Modal zum Erstellen eines Filters
        setNewFilterName(''); // Setze den Namen zurück
        setSelectedCategoriesForNewFilter([]); // Setze die ausgewählten Kategorien zurück
    };

    // NEU: Handler für Änderungen im Input-Feld des neuen Filternamens
    const handleNewFilterNameChange = (event) => {
        setNewFilterName(event.target.value);
    };

    // NEU: Handler für die Auswahl/Abwahl von Merkmalskategorien im Filter-Erstellungs-Modal
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

    // NEU: Handler zum Speichern des neuen Filters
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
                setShowAddFilterModal(false); // Modal schließen
                setNewFilterName(''); // Formular zurücksetzen
                setSelectedCategoriesForNewFilter([]); // Formular zurücksetzen
                fetchFilterData(); // Filterliste aktualisieren
            })
            .catch(error => console.error("Fehler beim Erstellen des Filters:", error.message));
    };

    // NEU: Handler zum Abbrechen der Filtererstellung
    const handleCancelAddFilter = () => {
        setShowAddFilterModal(false);
        setNewFilterName('');
        setSelectedCategoriesForNewFilter([]);
    };

    const handleBarClick = (data, index) => {
        setDialogContent(data); // Speichert die Daten für den Fall, dass sie benötigt werden
        setShowDialog(true); // NEU: Dialog anzeigen
    };

    const handleCloseDialog = () => {
        setShowDialog(false);
        setDialogContent(null); // Inhalt leeren
    };

    return (
        // Hauptcontainer der Anwendung: Jetzt nur noch mit der Klasse "app-container"
        <div className="app-container">

            {/* Die 9 Grid-Zellen, wobei die mittlere (Zelle 5) den tatsächlichen Inhalt enthält */}
            <div className="grid-cell-1">

            </div>
            <div className="grid-cell-2">
                <h1 className="header-title">Störungen</h1>
            </div>
            <div className="grid-cell-3">

            </div>
            <div className="grid-cell-4">

            </div>

            {/* Dieser Wrapper-Div ist die mittlere Zelle (5) des 3x3 Grids */}
            <div className="main-content-wrapper grid-cell-5">

                {/* Dropdown-Menü für Merkmalskategorie */}
                <div className="dropdown-container">
                    <label htmlFor="kategorieDropdown" className="dropdown-label">Merkmalskategorie:</label>
                    <select
                        id="kategorieDropdown"
                        value={selectedKategorie}
                        onChange={handleKategorieChange}
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
                        onChange={handleFilterChange}
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
                        onClick={handleAddFilter}
                        aria-label="Neuen Filter anlegen"
                        title="Neuen Filter anlegen"
                    >
                        +
                    </button>
                </div>
                {/* Diagramm-Container wurde von hier nach grid-cell-8 verschoben */}
            </div> {/* Ende des Wrapper-Divs für die mittlere Zelle */}

            <div className="grid-cell-6">

            </div>
            <div className="grid-cell-7">

            </div>

            <div className="grid-cell-8">
                {/* Diagramm-Container */}
                <div className="chart">
                    <ResponsiveContainer width="100%" height={400}>
                        <BarChart
                            data={data}
                            // margin={{ top: 20, right: 30, left: 20, bottom: 80 }}
                        >
                            <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
                            <XAxis
                                dataKey="merkmal"
                                angle={-45} // Winkel der Beschriftung
                                textAnchor="end" // Ausrichtung der Beschriftung
                                interval={0}    // Alle Beschriftungen anzeigen
                                height={100}    // Höhe für die Beschriftungen
                                tick={{ fill: '#4a4a4a', fontSize: 12 }} // Farbe und Größe der Beschriftung
                            />
                            <YAxis tick={{ fill: '#4a4a4a', fontSize: 12 }} />
                            <Tooltip
                                cursor={{ fill: 'rgba(0,0,0,0.1)' }} // Hintergrund des Tooltips beim Hovern
                                contentStyle={{ borderRadius: '8px', backgroundColor: '#fff', boxShadow: '0 2px 10px rgba(0,0,0,0.1)' }} // Stil des Tooltips
                                labelFormatter={(label, payload) => {
                                    const datum = payload[0]?.payload?.datum;
                                    return `Merkmal: ${label}` + (datum ? `, Datum: ${new Date(datum).toLocaleDateString()}` : '');
                                }}
                                formatter={(value, name, props) => [`${value}`, 'Summe']} // Formatierung des Wertes im Tooltip
                            />
                            <Legend wrapperStyle={{ paddingTop: '20px' }} />
                            <Bar
                                dataKey="summe"
                                fill="#60a5fa" // Schöneres Blau
                                onClick={handleBarClick} // Hier wird der Klick-Handler hinzugefügt
                                className="chart-bar-clickable"
                                radius={0}
                            />
                        </BarChart>
                    </ResponsiveContainer>
                </div>
            </div>
            <div className="grid-cell-9">
            </div>

            {/* NEU: Modales Dialogfenster */}
            {showDialog && dialogContent && (
                <div className="modal-overlay" onClick={handleCloseDialog}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <h2 className="dialog-title">Detailinformationen</h2>
                        <p className="dialog-text"><strong>Merkmal:</strong> {dialogContent.merkmal}</p>
                        <p className="dialog-text"><strong>Summe:</strong> {dialogContent.summe}</p>
                        <p className="dialog-text"><strong>Datum:</strong> {dialogContent.datum ? new Date(dialogContent.datum).toLocaleDateString('de-DE') : 'N/A'}</p>
                        <button className="dialog-close-button" onClick={handleCloseDialog} aria-label="Schließen">&times;</button>
                        {/* Iframes sind oft problematisch in Modal-Dialogen, da sie den Fokus entziehen oder Sicherheitsprobleme verursachen können.
                            Die folgende Zeile wird hier nur zu Demonstrationszwecken beibehalten, aber Vorsicht ist geboten!
                            Seiten wie bmw.de oder sap.com erlauben das Einbetten in iframes aus Sicherheitsgründen nicht.
                            Wenn Sie externe Inhalte anzeigen müssen, prüfen Sie deren CORS-Richtlinien.
                        */}
                        <iframe
                            className="embedded-iframe"
                            src="https://www.google.com/doodles"
                            title="Google Doodles"
                            width="100%"
                            height="400"
                            frameBorder="0"
                            allowFullScreen
                            sandbox="allow-scripts allow-same-origin allow-popups allow-forms"
                        >
                            Ihr Browser unterstützt leider keine Iframes.
                        </iframe>
                    </div>
                </div>
            )}

            {showAddFilterModal && (
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
            )}
        </div>
    );
}

export default App;
