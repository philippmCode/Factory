import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../assets/css/DashboardHome.css";

const initialFeatures = [
    { id: 1, name: "Failure reporting", icon: "💡", link: "/failure-reporting" },
    { id: 2, name: "Digital twin", icon: "🧠", link: "/digital-twin" },
];

const FeatureCard = ({ name, icon, link }) => (
    <Link to={link} className="feature-card-link">
        <div className="feature-card">
            <span className="feature-icon">{icon}</span>
            <h3 className="feature-name">{name}</h3>
        </div>
    </Link>
);

export default function DashboardHome() {
    const [features] = useState(initialFeatures);

    return (
        <div className="dashboard-container">
            <header>
                <h1>Factory Dashboard</h1>
            </header>

            <div className="feature-list">
                {features.map((feature) => (
                    <FeatureCard
                        key={feature.id}
                        name={feature.name}
                        icon={feature.icon}
                        link={feature.link}
                    />
                ))}
            </div>
        </div>
    );
}