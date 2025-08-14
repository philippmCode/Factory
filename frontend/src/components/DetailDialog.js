import React from 'react';

function DetailDialog({ show, content, onClose }) {
    // Wenn 'show' false ist oder kein Inhalt vorhanden ist, wird nichts gerendert.
    if (!show || !content) {
        return null;
    }

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <h2 className="dialog-title">Detailinformationen</h2>
                <p className="dialog-text"><strong>Merkmal:</strong> {content.merkmal}</p>
                <p className="dialog-text"><strong>Summe:</strong> {content.summe}</p>
                <p className="dialog-text"><strong>Datum:</strong> {content.datum ? new Date(content.datum).toLocaleDateString('de-DE') : 'N/A'}</p>
                <button className="dialog-close-button" onClick={onClose} aria-label="Schließen">&times;</button>
                {/* WICHTIGER HINWEIS ZU IFRAMES:
                    Seiten wie bmw.de oder sap.com erlauben das Einbetten in iframes aus Sicherheitsgründen nicht.
                    Dies ist nur ein Platzhalter und wird wahrscheinlich auf den meisten externen Websites nicht funktionieren.
                    Wenn Sie externe Inhalte anzeigen müssen, prüfen Sie deren CORS-Richtlinien und Sicherheitshinweise.
                */}
                <iframe
                    className="embedded-iframe"
                    src="https://www.google.com/doodles" // Beispiel-URL, wird für die meisten echten Seiten nicht funktionieren
                    title="Google Doodles"
                    width="100%"
                    height="400"
                    allowFullScreen
                    sandbox="allow-scripts allow-same-origin allow-popups allow-forms"
                >
                    Ihr Browser unterstützt leider keine Iframes.
                </iframe>
            </div>
        </div>
    );
}

export default DetailDialog;