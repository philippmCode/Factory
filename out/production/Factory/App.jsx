import React from "react";
import { Routes, Route } from "react-router-dom";

import DashboardHome from "./pages/DashboardHome.jsx";
import FailureReporting from "./pages/FailureReporting.jsx";
import DigitalTwin from "./pages/DigitalTwin.jsx";

export default function App() {
    return (
        <Routes>
            <Route path="/" element={<DashboardHome />} />
            <Route path="/failure-reporting" element={<FailureReporting />} />
            <Route path="/digital-twin" element={<DigitalTwin />} />
        </Routes>
    );
}