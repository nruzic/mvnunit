/**
 * Copyright (c) 2020 Nikola Ruzic
 */
package com.github.nruzic.mvnunit.simple;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.MetadataNotFoundException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transfer.TransferResource;

/**
 * A simplistic transfer listener that logs uploads/downloads to the console.
 * @author nruzic
 */
public class ConsoleTransferListener extends AbstractTransferListener
{
    private final PrintStream out;

    private final Map<TransferResource, Long> downloads = new ConcurrentHashMap<TransferResource, Long>();

    private int lastLength;

    /**
     * Instantiates a new console transfer listener.
     */
    public ConsoleTransferListener()
    {
        this(null);
    }

    /**
     * Instantiates a new console transfer listener.
     *
     * @param out
     *            the out
     */
    public ConsoleTransferListener(final PrintStream out)
    {
        this.out = (out != null) ? out : System.out;
    }

    @Override
    public void transferInitiated(final TransferEvent event)
    {
        final String message = event.getRequestType() == TransferEvent.RequestType.PUT ? "Uploading" : "Downloading";

        out.println(message + ": " + event.getResource().getRepositoryUrl() + event.getResource().getResourceName());
    }

    @Override
    public void transferProgressed(final TransferEvent event)
    {
        final TransferResource resource = event.getResource();
        downloads.put(resource, Long.valueOf(event.getTransferredBytes()));

        final StringBuilder buffer = new StringBuilder(64);

        for (final Map.Entry<TransferResource, Long> entry : downloads.entrySet())
        {
            final long total = entry.getKey().getContentLength();
            final long complete = entry.getValue().longValue();

            buffer.append(getStatus(complete, total)).append("  ");
        }

        final int pad = lastLength - buffer.length();
        lastLength = buffer.length();
        pad(buffer, pad);
        buffer.append('\r');

        out.print(buffer);
    }

    private String getStatus(final long complete, final long total)
    {
        if (total >= 1024)
        {
            return toKB(complete) + "/" + toKB(total) + " KB ";
        }
        else if (total >= 0)
        {
            return complete + "/" + total + " B ";
        }
        else if (complete >= 1024)
        {
            return toKB(complete) + " KB ";
        }
        else
        {
            return complete + " B ";
        }
    }

    private void pad(final StringBuilder buffer, int spaces)
    {
        final String block = "                                        ";
        while (spaces > 0)
        {
            final int n = Math.min(spaces, block.length());
            buffer.append(block, 0, n);
            spaces -= n;
        }
    }

    @Override
    public void transferSucceeded(final TransferEvent event)
    {
        transferCompleted(event);

        final TransferResource resource = event.getResource();
        final long contentLength = event.getTransferredBytes();
        if (contentLength >= 0)
        {
            final String type = (event.getRequestType() == TransferEvent.RequestType.PUT ? "Uploaded" : "Downloaded");
            final String len = contentLength >= 1024 ? toKB(contentLength) + " KB" : contentLength + " B";

            String throughput = "";
            final long duration = System.currentTimeMillis() - resource.getTransferStartTime();
            if (duration > 0)
            {
                final long bytes = contentLength - resource.getResumeOffset();
                final DecimalFormat format = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH));
                final double kbPerSec = (bytes / 1024.0) / (duration / 1000.0);
                throughput = " at " + format.format(kbPerSec) + " KB/sec";
            }

            out.println(type + ": " + resource.getRepositoryUrl() + resource.getResourceName() + " (" + len + throughput
                    + ")");
        }
    }

    @Override
    public void transferFailed(final TransferEvent event)
    {
        transferCompleted(event);

        if (!(event.getException() instanceof MetadataNotFoundException))
        {
            event.getException().printStackTrace(out);
        }
    }

    private void transferCompleted(final TransferEvent event)
    {
        downloads.remove(event.getResource());

        final StringBuilder buffer = new StringBuilder(64);
        pad(buffer, lastLength);
        buffer.append('\r');
        out.print(buffer);
    }

    @Override
    public void transferCorrupted(final TransferEvent event)
    {
        event.getException().printStackTrace(out);
    }

    protected long toKB(final long bytes)
    {
        return (bytes + 1023) / 1024;
    }

}
