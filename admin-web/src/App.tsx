import { useState } from 'react';
import './styles.css';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api/v1';

export default function App() {
  const [exerciseId, setExerciseId] = useState('wall_angels');
  const [file, setFile] = useState<File | null>(null);
  const [status, setStatus] = useState('');

  async function upload() {
    if (!file) return;
    setStatus('Requesting upload URL...');
    const response = await fetch(`${API_BASE}/admin/media/upload-url`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ exercise_id: exerciseId, filename: file.name, content_type: file.type || 'application/octet-stream' })
    });
    if (!response.ok) throw new Error('Could not create upload URL');
    const { upload_url } = await response.json();
    setStatus('Uploading media...');
    const uploadResponse = await fetch(upload_url, {
      method: 'PUT',
      headers: { 'Content-Type': file.type || 'application/octet-stream' },
      body: file
    });
    if (!uploadResponse.ok) throw new Error('Upload failed');
    setStatus('Uploaded successfully');
  }

  return (
    <main>
      <h1>CoreFit Admin</h1>
      <p>Manage exercise content and upload images/videos.</p>
      <label>Exercise ID<input value={exerciseId} onChange={e => setExerciseId(e.target.value)} /></label>
      <label>Media file<input type="file" accept="image/*,video/*" onChange={e => setFile(e.target.files?.[0] ?? null)} /></label>
      <button onClick={() => upload().catch(e => setStatus(e.message))} disabled={!file}>Upload</button>
      <p>{status}</p>
    </main>
  );
}
