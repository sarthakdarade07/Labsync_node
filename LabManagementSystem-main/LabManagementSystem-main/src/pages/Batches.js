import React, { useState, useEffect } from 'react';
import api from '../api/axios';
import { X, Plus } from 'lucide-react';

export default function Batches() {
  const [batches, setBatches] = useState([]);
  const [showAdd, setShowAdd] = useState(false);
  const [labs, setLabs] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [form, setForm] = useState({ batchName: '', division: '', semester: 'SEM-3', studentCount: '', osRequirement: 'Any', labsPerWeek: 1, totalHours: '', startTime: '', endTime: '', subjectIds: [] });

  const fetchBatches = () => api.get('/batches').then(res => { if (res.data?.success) setBatches(res.data.data); });
  useEffect(() => { 
    fetchBatches(); 
    api.get('/labs').then(res => { if (res.data?.success) setLabs(res.data.data); });
    api.get('/subjects').then(res => { if (res.data?.success) setSubjects(res.data.data); });
  }, []);

  const uniqueOs = Array.from(new Set(labs.map(l => l.osType).filter(Boolean)));

  const calculateEndTime = (startTime, totalHours) => {
    if (!startTime || !totalHours) return '';
    const [hours, minutes] = startTime.split(':').map(Number);
    if (isNaN(hours) || isNaN(minutes)) return '';
    const totalMins = hours * 60 + minutes + Number(totalHours) * 60;
    const endHours = Math.floor(totalMins / 60) % 24;
    const endMins = totalMins % 60;
    return `${endHours.toString().padStart(2, '0')}:${endMins.toString().padStart(2, '0')}`;
  };

  const addBatch = async () => {
    if (!form.batchName) return;
    try {
      const payload = { 
        ...form, 
        program: 1, 
        academicYear: 1,
        subjects: form.subjectIds,
        requiredHours: form.totalHours ? parseInt(form.totalHours) : 2
      };
      delete payload.subjectIds;
      if (!payload.startTime) payload.startTime = null;
      if (!payload.endTime) payload.endTime = null;
      if (!payload.studentCount) payload.studentCount = 30; // fallback default
      if (!payload.totalHours) payload.totalHours = null;
      if (!payload.labsPerWeek) payload.labsPerWeek = 1;

      const res = await api.post('/batches', payload);
      if (res.data?.success) {
        setForm({ batchName: '', division: '', semester: 'SEM-3', studentCount: '', osRequirement: 'Any', labsPerWeek: 1, totalHours: '', startTime: '', endTime: '', subjectIds: [] });
        setShowAdd(false);
        fetchBatches();
      }
    } catch (e) {}
  };

  const remove = async id => {
    try {
      if(window.confirm("Are you sure?")) {
        await api.delete(`/batches/${id}`);
        fetchBatches();
      }
    } catch(e) {}
  };

  return (
    <div style={styles.page} className="animate-in">
      <div style={styles.header}>
        <div>
          <div className="section-title">Academic Groups</div>
          <h1 className="page-title">Batches</h1>
        </div>
        <button className="btn btn-primary" onClick={() => setShowAdd(s => !s)} style={{ display: 'flex', alignItems: 'center', gap: 6 }}>
          {showAdd ? <><X size={16} /> Cancel</> : <><Plus size={16} /> Add Batch</>}
        </button>
      </div>

      {showAdd && (
        <div className="card" style={{ marginBottom: 24 }}>
          <div className="section-title">New Batch</div>
          <div className="grid-3" style={{ gap: 12, marginBottom: 14 }}>
            <div><label style={styles.label}>Batch Name</label><input placeholder="e.g. Batch A1" value={form.batchName} onChange={e => setForm(p => ({ ...p, batchName: e.target.value }))} /></div>
            <div><label style={styles.label}>Division</label><input placeholder="e.g. CSE-A" value={form.division} onChange={e => setForm(p => ({ ...p, division: e.target.value }))} /></div>
            <div><label style={styles.label}>Semester</label>
              <select value={form.semester} onChange={e => setForm(p => ({ ...p, semester: e.target.value }))}>
                <option value="">Select Semester</option>
                {[1, 2, 3, 4, 5, 6, 7, 8].map(s => <option key={s} value={`SEM-${s}`}>SEM-{s}</option>)}
              </select>
            </div>
            <div><label style={styles.label}>Strength</label><input type="number" placeholder="e.g. 30" value={form.studentCount} onChange={e => setForm(p => ({ ...p, studentCount: parseInt(e.target.value)||30 }))} /></div>
            <div><label style={styles.label}>OS Req</label>
              <select value={form.osRequirement} onChange={e => setForm(p => ({ ...p, osRequirement: e.target.value }))}>
                <option value="Any">Any</option>
                {uniqueOs.map(os => <option key={os} value={os}>{os}</option>)}
              </select>
            </div>
            <div><label style={styles.label}>Labs/Wk</label><input type="number" placeholder="e.g. 1" min="1" value={form.labsPerWeek} onChange={e => setForm(p => ({ ...p, labsPerWeek: parseInt(e.target.value)||1 }))} /></div>
            <div><label style={styles.label}>Total Hrs</label><input type="number" placeholder="e.g. 50" value={form.totalHours} onChange={e => {
              const totalHours = parseInt(e.target.value) || '';
              setForm(p => ({ ...p, totalHours, endTime: calculateEndTime(p.startTime, totalHours) || p.endTime }));
            }} /></div>
            <div><label style={styles.label}>Start Time</label><input type="time" value={form.startTime} onChange={e => {
              const startTime = e.target.value;
              setForm(p => ({ ...p, startTime, endTime: calculateEndTime(startTime, p.totalHours) || p.endTime }));
            }} /></div>
            <div><label style={styles.label}>End Time</label><input type="time" value={form.endTime} onChange={e => setForm(p => ({ ...p, endTime: e.target.value }))} /></div>
            
            <div style={{ gridColumn: '1 / -1' }}><label style={styles.label}>Mapped Subjects (Select multiple)</label>
              <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap', border: '1px solid var(--border)', padding: 12, borderRadius: 8, maxHeight: 150, overflowY: 'auto' }}>
                {subjects.map(s => {
                  const sId = s._id || s.id;
                  return (
                  <label key={sId} style={{ display: 'flex', alignItems: 'center', gap: 6, fontSize: 13, background: 'var(--bg2)', padding: '4px 8px', borderRadius: 4, cursor: 'pointer' }}>
                    <input type="checkbox" checked={form.subjectIds.includes(sId)} onChange={e => {
                      const checked = e.target.checked;
                      setForm(p => ({ ...p, subjectIds: checked ? [...p.subjectIds, sId] : p.subjectIds.filter(id => id !== sId) }));
                    }} />
                    {s.name}
                  </label>
                )})}
                {subjects.length === 0 && <div style={{ fontSize: 13, color: 'var(--text3)' }}>No subjects available.</div>}
              </div>
            </div>
    
          </div>
          <button className="btn btn-primary" onClick={addBatch}>Add Batch</button>
        </div>
      )}

      <div className="card">
        <div className="section-title">All Batches — {batches.length} groups</div>
        <table>
          <thead><tr><th>Batch ID</th><th>Name</th><th>Division</th><th>Year</th><th>Strength</th><th>OS</th><th>Subjects</th><th>Labs/Wk</th><th>Time (Start-End) / Hrs</th><th>Actions</th></tr></thead>
          <tbody>
            {batches.map(b => {
              const bId = b._id || b.id;
              return (
              <tr key={bId}>
                <td style={{ fontFamily: 'var(--mono)', color: 'var(--accent)' }}>{bId}</td>
                <td style={{ color: 'var(--text)', fontWeight: 600 }}>{b.batchName}</td>
                <td>{b.division}</td>
                <td><span className="badge badge-info">{b.semester}</span></td>
                <td style={{ fontFamily: 'var(--mono)' }}>{b.studentCount}</td>
                <td><span className="badge">{b.osRequirement || 'Any'}</span></td>
                <td style={{ fontSize: 12, color: 'var(--text2)', maxWidth: 150 }}>{b.subjects && b.subjects.length > 0 ? b.subjects.map(s => s.name).join(', ') : 'None'}</td>
                <td style={{ textAlign: 'center' }}>{b.labsPerWeek || 1}</td>
                <td style={{ fontSize: 13 }}>
                  {(!b.startTime || b.startTime === '00:00:00' || b.startTime === '00:00') ? 'Any' : b.startTime.substring(0,5)} - 
                  {(!b.endTime || b.endTime === '00:00:00' || b.endTime === '00:00') ? 'Any' : b.endTime.substring(0,5)} 
                  ({b.totalHours ? b.totalHours + 'h' : 'N/A'})
                </td>
    
                <td>
                  <button className="btn btn-danger btn-sm" onClick={() => remove(bId)}>Remove</button>
                </td>
              </tr>
            )})}
          </tbody>
        </table>
      </div>
    </div>
  );
}

const styles = {
  page: { padding: 32, maxWidth: 1200 },
  header: { display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end', marginBottom: 28 },
  label: { display: 'block', fontSize: 11, fontFamily: 'var(--mono)', textTransform: 'uppercase', letterSpacing: 0.5, color: 'var(--text3)', marginBottom: 6 },
};
