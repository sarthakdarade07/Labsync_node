import React, { useState, useEffect } from 'react';
import api from '../api/axios';

export default function Timetable() {
  const [filterBatch, setFilterBatch] = useState('all');
  const [filterLab, setFilterLab] = useState('all');
  const [labs, setLabs] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [batches, setBatches] = useState([]);
  const [days, setDays] = useState([]);
  const [sessions, setSessions] = useState([]);

  useEffect(() => {
    api.get('/labs').then(res => { if (res.data?.success) setLabs(res.data.data); });
    api.get('/subjects').then(res => { if (res.data?.success) setSubjects(res.data.data); });
    api.get('/batches').then(res => { if (res.data?.success) setBatches(res.data.data); });
    api.get('/days').then(res => { if (res.data?.success) setDays(res.data.data); });
    api.get('/schedules').then(res => { if (res.data?.success) setSessions(res.data.data); });
  }, []);

  const filtered = sessions.filter(s =>
    (filterBatch === 'all' || s.batchId.toString() === filterBatch.toString()) &&
    (filterLab   === 'all' || s.labId.toString()   === filterLab.toString())
  );

  const dynamicTimeSlots = React.useMemo(() => {
    const slots = new Set();
    filtered.forEach(s => {
      if (s.slotLabel) slots.add(s.slotLabel);
    });
    return Array.from(slots).sort().map((label, idx) => ({ id: 'dyn_' + idx, slotLabel: label }));
  }, [filtered]);

  const cellKey = (day, slot) => `${day}|${slot}`;
  const sessionMap = {};
  filtered.forEach(s => {
    const k = cellKey(s.dayName, s.slotLabel);
    if (!sessionMap[k]) sessionMap[k] = [];
    sessionMap[k].push(s);
  });

  const batchMap = Object.fromEntries(batches.map(b => [b.id, b]));
  const subjectMap = Object.fromEntries(subjects.map(s => [s.id, s]));

  const colours = ['#3b82f6','#8b5cf6','#06b6d4','#10b981','#f59e0b','#ef4444'];
  const batchColour = {};
  batches.forEach((b, i) => { batchColour[b.id] = colours[i % colours.length]; });

  const printTimetable = () => window.print();

  return (
    <div style={styles.page} className="animate-in">
      <div style={styles.header}>
        <div>
          <div className="section-title">Visual Grid</div>
          <h1 className="page-title">Timetable</h1>
        </div>
        <div style={{ display: 'flex', gap: 10, alignItems: 'center' }}>
          <select value={filterBatch} onChange={e => setFilterBatch(e.target.value)} style={{ width: 'auto' }}>
            <option value="all">All Batches</option>
            {batches.map(b => <option key={b.id} value={b.id}>{b.batchName}</option>)}
          </select>
          <select value={filterLab} onChange={e => setFilterLab(e.target.value)} style={{ width: 'auto' }}>
            <option value="all">All Labs</option>
            {labs.map(l => <option key={l.id} value={l.id}>{l.labName}</option>)}
          </select>
          <button className="btn btn-secondary" onClick={printTimetable}>🖨 Print</button>
        </div>
      </div>

      <div className="card" style={{ overflowX: 'auto' }}>
        <table style={{ minWidth: 800 }}>
          <thead>
            <tr>
              <th style={{ width: 100, position: 'sticky', left: 0, background: 'var(--card)', zIndex: 1 }}>Day / Slot</th>
              {dynamicTimeSlots.map(slot => <th key={slot.id} style={{ fontSize: 10 }}>{slot.slotLabel}</th>)}
            </tr>
          </thead>
          <tbody>
            {days.map(day => (
              <tr key={day.id}>
                <td style={{ fontWeight: 700, color: 'var(--accent)', fontFamily: 'var(--mono)', fontSize: 12, position: 'sticky', left: 0, background: 'var(--card)', zIndex: 1, padding: '14px 16px' }}>
                  {day.dayName.slice(0, 3).toUpperCase()}
                </td>
                {dynamicTimeSlots.map(slot => {
                  const entries = sessionMap[cellKey(day.dayName, slot.slotLabel)] || [];
                  return (
                    <td key={slot.id} style={{ padding: 4, verticalAlign: 'top', minWidth: 120 }}>
                      {entries.map(s => {
                        const batch = batchMap[s.batchId];
                        const subject = subjectMap[s.subjectId];
                        const col = batchColour[s.batchId] ?? 'var(--accent)';
                        return (
                          <div key={s.scheduleId} style={{ ...styles.cell, borderLeft: `3px solid ${col}`, background: `${col}18` }}>
                            <div style={{ fontSize: 11, fontWeight: 700, color: col, fontFamily: 'var(--mono)' }}>{s.batchName}</div>
                            <div style={{ fontSize: 10, color: 'var(--text2)', marginTop: 2 }}>{s.subjectCode}</div>
                            <div style={{ fontSize: 10, color: 'var(--text3)', marginTop: 1 }}>{s.labName?.split(' —')[0]}</div>
                          </div>
                        );
                      })}
                    </td>
                  );
                })}
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Legend */}
      <div className="card" style={{ marginTop: 16 }}>
        <div className="section-title">Batch Legend</div>
        <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap' }}>
          {batches.map(b => (
            <div key={b.id} style={{ display: 'flex', alignItems: 'center', gap: 6 }}>
              <div style={{ width: 12, height: 12, borderRadius: 3, background: batchColour[b.id] }} />
              <span style={{ fontSize: 12, color: 'var(--text2)' }}>{b.batchName} ({b.division})</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

const styles = {
  page: { padding: 32, maxWidth: 1400 },
  header: { display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end', marginBottom: 28, flexWrap: 'wrap', gap: 12 },
  cell: { padding: '5px 8px', borderRadius: 5, marginBottom: 3, cursor: 'default' },
};
