import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from 'recharts';

function BarChartDisplay({ data, onBarClick }) {
    return (
        // Diagramm-Container
        <div className="chart">
            <ResponsiveContainer width="100%" height={400}>
                <BarChart
                    data={data}
                    // margin={{ top: 20, right: 30, left: 20, bottom: 80 }} // Kommentar belassen, kann bei Bedarf aktiviert werden
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
                        onClick={onBarClick} // Hier wird der Klick-Handler hinzugefügt
                        className="chart-bar-clickable"
                        radius={0}
                    />
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
}

export default BarChartDisplay;